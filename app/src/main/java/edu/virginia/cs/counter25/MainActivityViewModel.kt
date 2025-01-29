package edu.virginia.cs.counter25

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import edu.virginia.cs.counter25.model.Counter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class MainActivityViewModel(
    private val initialCounters: List<Counter> = listOf(),
    private val accordionsInitiallyExpanded: Boolean = false,
): ViewModel() {
    /** Accordion State */
    private val _accordion1State = MutableStateFlow(accordionsInitiallyExpanded)
    val accordion1State = _accordion1State.asStateFlow()

    private val _accordion2State = MutableStateFlow(accordionsInitiallyExpanded)
    val accordion2State = _accordion1State.asStateFlow()

    fun accordion1Toggle() {
        println("Accordion1 Toggle")
        _accordion1State.update { currentValue -> !currentValue }
    }

    fun accordion2Toggle() {
        println("Accordion2 Toggle")
        _accordion2State.update { currentValue -> !currentValue }
    }

    /** Counters List state */

    private val _counters = initialCounters.toMutableStateList()
    val countersState get() = _counters.toList()

    val size get() = _counters.size

    fun addCounter(newName: String) {
        _counters.add(Counter(name = newName))
    }

    fun incrementCounter(index: Int) {
        _counters[index] = _counters[index].copy(
            value = _counters[index].value - 1
        )
    }

    fun decrementCounter(index: Int) {
        _counters[index] = _counters[index].copy(
            value = _counters[index].value - 1
        )
    }

    fun resetCounter(index: Int) {
        _counters[index] = _counters[index].copy(
            value = 0
        )
    }

    fun renameCounter(index: Int, newName: String) {
        _counters[index] = _counters[index].copy(
            name = newName
        )
    }
}