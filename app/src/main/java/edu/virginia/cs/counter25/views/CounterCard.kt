package edu.virginia.cs.counter25.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import edu.virginia.cs.counter25.model.Counter

@Composable
fun CounterCard(
    initialCounter: Counter
) {
    var counter by remember { mutableStateOf(initialCounter) }
    Column {
        Row {
            Text(text = "${counter.value}", style = MaterialTheme.typography.titleLarge)
        }
        Row {
            Button(
                onClick = {  }
            ) {
                Image(Icons.Default.KeyboardArrowUp, contentDescription = null)
            }
            Button(
                onClick = {  },
                enabled = counter.value > 0
            ) {
                Image(Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
            Button(
                onClick = {  }
            ) {
                Image(Icons.Filled.Refresh, contentDescription = null)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CounterCardPreview() {
    CounterCard(Counter("Counter = 0"))
}

@Preview(showBackground = true)
@Composable
fun CounterCardNonZeroPreview() {
    CounterCard(Counter("Counter = 1"))
}