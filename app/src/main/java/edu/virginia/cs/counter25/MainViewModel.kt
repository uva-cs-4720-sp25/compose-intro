package edu.virginia.cs.counter25

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.virginia.cs.counter25.model.Counter
import edu.virginia.cs.counter25.model.CounterRepository
import edu.virginia.cs.counter25.model.UserPreferences
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class AccordionState(
    val accordion1Expanded: Boolean = false,
    val accordion2Expanded: Boolean = false,
    val isAccordion2Loading: Boolean = false
)


class MainActivityViewModel(
    private val counterRepository: CounterRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _accordionState = MutableStateFlow(AccordionState())
    val accordionState: StateFlow<AccordionState> = _accordionState.asStateFlow()

    private val _countersState = MutableStateFlow(emptyList<Counter>())
    val countersState = _countersState.asStateFlow()

    init {
        viewModelScope.launch(IO) {
            counterRepository.getAll().collectLatest { data ->
                _countersState.tryEmit(data)
            }
        }
        viewModelScope.launch {
            userPreferences.getBooleanPreference(UserPreferences.PREFERENCE_ACCORDION_KEY)
                .collectLatest { isExpanded ->
                    _accordionState.update { currentState ->
                        currentState.copy(
                            accordion1Expanded = isExpanded
                        )
                    }
                }
        }
    }

    fun accordion1Toggle() {
        viewModelScope.launch {
            val newAccordionExpanded = !_accordionState.value.accordion1Expanded
            _accordionState.update { currentState ->
                currentState.copy(
                    accordion1Expanded = newAccordionExpanded
                )
            }
            userPreferences.saveBooleanPreference(
                UserPreferences.PREFERENCE_ACCORDION_KEY,
                newAccordionExpanded
            )
        }
    }

    fun accordion2Toggle() {
        viewModelScope.launch {
            _accordionState.update { currentState -> currentState.copy(isAccordion2Loading = true) }

            // The following is designed to mimic, say, loading text from a very slow internet server
            if (!_accordionState.value.accordion2Expanded) {
                delay(3000)
            }
            _accordionState.update { currentState ->
                currentState.copy(
                    accordion2Expanded = !currentState.accordion2Expanded,
                    isAccordion2Loading = false
                )
            }
        }
    }

    val size get() = _countersState.value.size

    fun refreshCounters() {
        viewModelScope.launch(IO) {
            _countersState.emit(counterRepository.getAll().first())
        }
    }

    fun addCounter(newName: String) {
        viewModelScope.launch(IO) {
            counterRepository.addCounter(Counter(name = newName))
        }
    }

    fun incrementCounter(counter: Counter) {
        viewModelScope.launch(IO) {
            counterRepository.updateCounter(counter.increment())
        }
    }

    fun decrementCounter(counter: Counter) {
        viewModelScope.launch(IO) {
            counterRepository.updateCounter(counter.decrement())
        }
    }

    fun resetCounter(counter: Counter) {
        viewModelScope.launch(IO) {
            counterRepository.updateCounter(counter.reset())
        }
    }

    fun deleteCounter(counter: Counter) {
        viewModelScope.launch(IO) {
            counterRepository.deleteCounter(counter)
        }
    }
}