package edu.virginia.cs.counter25

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.virginia.cs.counter25.model.Counter
import edu.virginia.cs.counter25.model.CounterDao
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(
    private val counterId: Long,
    private val counterDao: CounterDao
): ViewModel() {
    private val _counterState = MutableStateFlow(Counter(""))
    val counterState = _counterState.asStateFlow()

    init {
        viewModelScope.launch(IO) {
            counterDao.getCounterById(counterId).collectLatest { counter ->
                _counterState.tryEmit(counter)
            }
        }
    }

    fun renameCounter(newName: String) {
        viewModelScope.launch(IO) {
            val updatedCounter = _counterState.value.rename(newName)
            counterDao.updateCounter(updatedCounter)
        }
    }
}