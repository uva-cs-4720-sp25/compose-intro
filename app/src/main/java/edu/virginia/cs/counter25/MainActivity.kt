package edu.virginia.cs.counter25

import android.app.Activity
import android.content.Intent
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.room.Room
import edu.virginia.cs.counter25.model.CounterDatabase
import edu.virginia.cs.counter25.ui.theme.Counter25Theme
import edu.virginia.cs.counter25.views.Accordion
import edu.virginia.cs.counter25.views.CounterCard

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            CounterDatabase::class.java,
            "counters.db"
        ).build()
    }
    private val viewModel by viewModels<MainActivityViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainActivityViewModel(database.counterDao()) as T
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
            CountersSection(viewModel)
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
    fun CountersSection(
        viewModel: MainActivityViewModel
    ) {
        val counters by viewModel.countersState.collectAsState()
        val context = LocalContext.current
        val detailLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
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



