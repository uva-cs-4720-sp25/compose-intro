package edu.virginia.cs.counter25

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import edu.virginia.cs.counter25.ui.theme.Counter25Theme
import edu.virginia.cs.counter25.views.Accordion
import edu.virginia.cs.counter25.views.CounterCard
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        println("MainActivity created")
        super.onCreate(savedInstanceState)


        val viewModel:MainActivityViewModel by viewModels<MainActivityViewModel>()

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
        val uiState by viewModel.uiState.collectAsState()

        Column {
            Accordion(
                headerText = "Welcome to the counters app",
                bodyText = "Below is an application to demonstrate the basics of a view model",
                isExpanded = uiState.accordion1Expanded,
                onHeaderClick = { viewModel.accordion1Toggle() }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Accordion(
                headerText = "About the app...",
                bodyText = "Below is a counter. Use the up arrow to increment the number, and the down arrow to decrement the number. The refresh button will reset the number to zero. Enjoy!",
                isExpanded = uiState.accordion2Expanded
            ) { viewModel.accordion2Toggle() }

            Spacer(modifier = Modifier.height(4.dp))
            LazyColumn {
                itemsIndexed(uiState.counters) { index, counter ->
                    CounterCard(
                        counter = counter,
                        onIncrement = { viewModel.incrementCounter(index) },
                        onDecrement = { viewModel.decrementCounter(index) },
                        onReset = { viewModel.resetCounter(index) },
                        onDelete = { viewModel.deleteCounter(index) },
                    )
                }
            }
            IconButton({ viewModel.addCounter("Counter #${viewModel.size + 1}") }) {
                Icon(Icons.Default.Add, contentDescription = "add counter")
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        fontSize = 32.sp,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Counter25Theme {
        Greeting("Android")
    }
}


