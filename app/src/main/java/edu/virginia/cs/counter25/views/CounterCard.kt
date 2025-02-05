package edu.virginia.cs.counter25.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.virginia.cs.counter25.model.Counter

@Composable
fun CounterCard(
    counter: Counter,
    onIncrementClick: () -> Unit = {},
    onDecrementClick: () -> Unit = {},
    onResetClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onDetail: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = "${counter.name}", style = MaterialTheme.typography.titleMedium)
                    Text(text = "${counter.value}", style = MaterialTheme.typography.titleLarge)
                }
                IconButton(
                    onClick = onDetail
                ) {
                    Image(Icons.Filled.Edit, contentDescription = null)
                }
            }
            Row(
               modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.weight(0.25f),
                    onClick = onIncrementClick
                ) {
                    Image(Icons.Default.KeyboardArrowUp, contentDescription = null)
                }
                Button(
                    modifier = Modifier.weight(0.25f),
                    onClick = onDecrementClick,
                    enabled = counter.isDecrementable()
                ) {
                    Image(Icons.Default.KeyboardArrowDown, contentDescription = null)
                }
                Button(
                    modifier = Modifier.weight(0.25f),
                    onClick = onResetClick,
                ) {
                    Image(Icons.Filled.Refresh, contentDescription = null)
                }
                Button(
                    modifier = Modifier.weight(0.25f),
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
    CounterCard(Counter("Counter = 0"))
}

@Preview(showBackground = true)
@Composable
fun CounterCardNonZeroPreview() {
    CounterCard(Counter("Counter = 1"))
}