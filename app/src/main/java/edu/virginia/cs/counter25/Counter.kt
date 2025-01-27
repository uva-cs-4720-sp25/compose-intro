package edu.virginia.cs.counter25

data class Counter(
    val value: Int = 0
) {
    fun increment(): Counter {
        return this.copy(value = value + 1)
    }

    fun decrement(): Counter {
        return this.copy(value = value - 1)
    }

    fun reset(): Counter {
        return this.copy(value = 0)
    }
}
