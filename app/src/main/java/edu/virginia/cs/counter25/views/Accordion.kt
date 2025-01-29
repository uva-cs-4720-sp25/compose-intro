package edu.virginia.cs.counter25.views

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun Accordion(
    headerText: String,
    bodyText: String,
    isExpanded: Boolean = false,
    toggleExpansion: (Unit) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.clickable { toggleExpansion }
        ) {
            Text(headerText, style = MaterialTheme.typography.titleLarge)
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
                BasicText(text = bodyText, style = MaterialTheme.typography.bodyLarge)
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
    ) {}
}

@Preview(showBackground = true)
@Composable
fun AccordionExpandedPreview() {
    Accordion(
        headerText = "Preview Header",
        bodyText = LoremIpsum(50).values.joinToString(" "),
        isExpanded = true
    ) {}
}