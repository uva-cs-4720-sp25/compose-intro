package edu.virginia.cs.counter25

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.virginia.cs.counter25.ui.theme.Counter25Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var counter = Counter()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Counter25Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Accordion(
                            "Counter App",
                            "Below is a counter. Use the up arrow to increment the number, and the down arrow to decrement the number. The refresh button will reset the number to zero. Enjoy!"
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        CounterCard(counter)
                    }
                }
            }
        }
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

@Composable
fun Accordion(
    headerText: String,
    bodyText: String,
    initiallyVisible: Boolean = false
) {
    var isExpanded by remember { mutableStateOf(initiallyVisible) }
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.clickable { isExpanded = !isExpanded }
        ) {
            Text(headerText, style = MaterialTheme.typography.titleLarge )
            Image(Icons.Default.KeyboardArrowDown, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(4.dp))
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessMedium,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically()
        ) {
            Box {
                BasicText(text = bodyText, style = MaterialTheme.typography.bodyLarge )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccordionCollapsedPreview() {
    Accordion(
        headerText = "Preview Header",
        bodyText = LoremIpsum(100).values.joinToString(" ")
    )
}

@Preview(showBackground = true)
@Composable
fun AccordionExpandedPreview() {
    Accordion(
        headerText = "Preview Header",
        bodyText = LoremIpsum(50).values.joinToString(" "),
        initiallyVisible = true
    )
}

@Composable
fun CounterCard(
    initialCounter: Counter = Counter(0)
) {
    var counter by remember { mutableStateOf(initialCounter) }
    Column {
        Row {
            Text(text = "${counter.value}", style = MaterialTheme.typography.titleLarge)
        }
        Row {
            Button(
                onClick = { counter = counter.increment() }
            ) {
                Image(Icons.Default.KeyboardArrowUp, contentDescription = null)
            }
            Button(
                onClick = { counter = counter.decrement() },
                enabled = counter.value > 0
            ) {
                Image(Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
            Button(
                onClick = { counter = counter.reset() }
            ) {
                Image(Icons.Filled.Refresh, contentDescription = null)
            }
        }
    }
}
