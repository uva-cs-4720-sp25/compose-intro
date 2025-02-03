package edu.virginia.cs.counter25.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.virginia.cs.counter25.MainActivityViewModel
import edu.virginia.cs.counter25.model.Counter
import kotlinx.coroutines.launch

@Composable
fun CounterCard(
    counter: Counter,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit,
    onResetClick: () -> Unit,
    onDeleteClick:() -> Unit
) {
    Surface(
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(text = "${counter.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "${counter.value}", style = MaterialTheme.typography.titleLarge)
            Row {
                Button(
                    onClick = onIncrementClick
                ) {
                    Image(Icons.Default.KeyboardArrowUp, contentDescription = null)
                }
                Button(
                    onClick = onDecrementClick,
                    enabled = counter.isDecrementable()
                ) {
                    Image(Icons.Default.KeyboardArrowDown, contentDescription = null)
                }
                Button(
                    onClick = onResetClick,
                ) {
                    Image(Icons.Filled.Refresh, contentDescription = null)
                }
                Button(
                    onClick = onDeleteClick
                ) {
                    Image(Icons.Filled.Delete, contentDescription = null)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CounterCardPreview() {
    CounterCard(Counter("Counter = 0"), {}, {}, {}, {})
}

@Preview(showBackground = true)
@Composable
fun CounterCardNonZeroPreview() {
    CounterCard(Counter("Counter = 1"), {}, {}, {}, {})
}