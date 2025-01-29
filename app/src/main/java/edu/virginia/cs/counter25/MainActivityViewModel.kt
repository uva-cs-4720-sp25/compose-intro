package edu.virginia.cs.counter25

import androidx.lifecycle.ViewModel
import edu.virginia.cs.counter25.model.Counter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class MainUIState(
    val accordion1Expanded: Boolean = false,
    val accordion2Expanded: Boolean = false,
    val counters: List<Counter> = listOf()
)

class MainActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()


    fun accordion1Toggle() {
        _uiState.update { currentState ->
            currentState.copy(
                accordion1Expanded = !currentState.accordion1Expanded
            )
        }
    }

    fun accordion2Toggle() {
        _uiState.update { currentState ->
            currentState.copy(
                accordion2Expanded = !currentState.accordion2Expanded
            )
        }
    }

    val size get() = _uiState.value.counters.size

    fun addCounter(newName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                counters = currentState.counters + Counter(newName)
            )
        }
    }

    fun incrementCounter(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                counters = currentState.counters.mapIndexed{ i, c ->
                    if (i == index) c.copy(value = c.value + 1) else c
                }
            )
        }
    }

    fun decrementCounter(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                counters = currentState.counters.mapIndexed{ i, c ->
                    if (i == index && c.isDecrementable()) c.copy(value = c.value - 1) else c
                }
            )
        }
    }

    fun resetCounter(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                counters = currentState.counters.mapIndexed{ i, c ->
                    if (i == index) c.copy(value = 0) else c
                }
            )
        }
    }

    fun renameCounter(index: Int, newName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                counters = currentState.counters.mapIndexed{ i, c ->
                    if (i == index) c.copy(name = newName) else c
                }
            )
        }
    }

    fun deleteCounter(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                counters = currentState.counters.filterIndexed { ind, _ ->
                    ind != index
                }
            )
        }
    }
}