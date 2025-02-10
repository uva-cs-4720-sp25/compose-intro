package edu.virginia.cs.counter25.model

import kotlinx.coroutines.flow.Flow

class CounterRepository(
    private val localCounterSource: CounterDao
) {
    fun getCounterById(id: Long): Flow<Counter> {
        return localCounterSource.getCounterById(id)
    }

    fun getAll(): Flow<List<Counter>> {
        return localCounterSource.getAll()
    }

    suspend fun addCounter(counter: Counter) {
        localCounterSource.addCounter(counter)
    }

    suspend fun updateCounter(counter: Counter) {
        localCounterSource.updateCounter(counter)
    }

    suspend fun deleteCounter(counter: Counter) {
        localCounterSource.deleteCounter(counter)
    }
}