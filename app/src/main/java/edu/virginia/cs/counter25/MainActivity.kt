package edu.virginia.cs.counter25


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.virginia.cs.counter25.model.CounterDatabase
import edu.virginia.cs.counter25.model.CounterRepository
import edu.virginia.cs.counter25.model.UserPreferences
import edu.virginia.cs.counter25.ui.theme.Counter25Theme
import edu.virginia.cs.counter25.views.Accordion
import edu.virginia.cs.counter25.views.CounterCard


const val COUNTING_TUTORIAL_URL = "https://www.youtube.com/watch?v=2AoxCkySv34"
const val COUNTING_VIDEO_CODE = "2AoxCkySv34"

class MainActivity : ComponentActivity() {
    private val counterRepository by lazy {
        CounterRepository(CounterDatabase.getDatabase(applicationContext).counterDao())
    }

    private val userPreferences by lazy {
        UserPreferences.getInstance(applicationContext)
    }

    @Suppress("UNCHECKED_CAST")
    private val viewModel by viewModels<MainActivityViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainActivityViewModel(counterRepository, userPreferences) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        println("Main Activity: Created")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Counter25Theme {
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        MainActivityScreen(viewModel)
                    }
                }
            }
        }
    }

    @Composable
    private fun MainActivityScreen(
        viewModel: MainActivityViewModel
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Accordions(viewModel)
            Spacer(modifier = Modifier.height(4.dp))
            HowToCount()
            Spacer(modifier = Modifier.height(4.dp))
            CountersList(viewModel)
        }
    }

    @Composable
    fun Accordions(
        viewModel: MainActivityViewModel
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val accordionState by viewModel.accordionState.collectAsState()
            Accordion(
                headerText = "Welcome to the counters app",
                bodyText = "Below is an application to demonstrate the basics of a view model",
                isExpanded = accordionState.accordion1Expanded,
                onHeaderClick = { viewModel.accordion1Toggle() }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Accordion(
                headerText = "About the app...",
                bodyText = "Below is a counter. Use the up arrow to increment the number, and the down arrow to decrement the number. The refresh button will reset the number to zero. Enjoy!",
                isLoading = accordionState.isAccordion2Loading,
                isExpanded = accordionState.accordion2Expanded,
                onHeaderClick = { viewModel.accordion2Toggle() }
            )
        }
    }

    @Composable
    fun HowToCount() {
        val context = LocalContext.current
        TextButton(
            onClick = { launchGenericBrowserIntent(context) },
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp)
        ) {
            Text("Tutorial on counting")
        }
    }

    private fun launchGenericBrowserIntent(context: Context) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(COUNTING_TUTORIAL_URL))
        context.startActivity(webIntent)
    }

    private fun launchYoutubeIntent(context: Context) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$COUNTING_VIDEO_CODE"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(COUNTING_TUTORIAL_URL))

        try {
            context.startActivity(appIntent) // Try to open in YouTube app
        } catch (e: ActivityNotFoundException) {
            context.startActivity(webIntent) // Fallback to browser if YouTube app is not installed
        }
    }

    private fun launchChromeIntent(context: Context) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(COUNTING_TUTORIAL_URL)
        ).apply {
            setPackage("com.android.chrome") // Force opening in Chrome
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(COUNTING_TUTORIAL_URL))
            context.startActivity(fallbackIntent)
        }
    }


    @Composable
    fun CountersList(
        viewModel: MainActivityViewModel
    ) {
        val counters by viewModel.countersState.collectAsState()
        val context = LocalContext.current
        val detailLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.refreshCounters()
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(counters) { counter ->
                    CounterCard(
                        counter = counter,
                        onIncrementClick = { viewModel.incrementCounter(counter) },
                        onDecrementClick = { viewModel.decrementCounter(counter) },
                        onResetClick = { viewModel.resetCounter(counter) },
                        onDeleteClick = { viewModel.deleteCounter(counter) },
                        onDetail = {
                            val detailIntent = Intent(
                                context,
                                DetailActivity::class.java
                            ).apply {
                                putExtra("id", counter.id)
                            }
                            detailLauncher.launch(detailIntent)
                        }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton({ viewModel.addCounter("Counter #${viewModel.size + 1}") }) {
                    Icon(
                        Icons.TwoTone.Add,
                        contentDescription = "add counter",
                        tint = Color.White,
                        modifier = Modifier.size(120.dp)
                            .background(MaterialTheme.colorScheme.secondary)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }

    override fun onResume() {
        println("Main Activity: Resumed")
        super.onResume()
    }

    override fun onStart() {
        println("Main Activity: Started")
        super.onStart()
    }

    override fun onPause() {
        println("Main Activity: Paused")
        super.onPause()
    }

    override fun onStop() {
        println("Main Activity: Stopped")
        super.onStop()
    }

    override fun onDestroy() {
        println("Main Activity: Destroyed")
        super.onDestroy()
    }
}



