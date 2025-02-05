package edu.virginia.cs.counter25.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.virginia.cs.counter25.model.Counter

@Composable
fun CounterDetail(
    counter: Counter,
    onNameSave: (String) -> Unit = {},
    onFinish: () -> Unit = {}
) {
    var nameEntry by rememberSaveable { mutableStateOf(counter.name) }
    var savedRecently by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Current Name: ${counter.name}")

        OutlinedTextField(
            value = nameEntry,
            onValueChange = {
                newEntry -> nameEntry = newEntry
                savedRecently = false
            },
            label = { Text("Counter Name") }
        )
        Row {
            TextButton(
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                    onNameSave(nameEntry)
                    savedRecently = true
                },

            ) {
                Text("Save")
            }
            if (savedRecently) { Text("just saved!") }
        }
        TextButton(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
            onClick = onFinish
        ) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterDetailPreview() {
    CounterDetail(Counter("Counter = 0"))
}