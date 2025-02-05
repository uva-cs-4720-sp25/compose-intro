package edu.virginia.cs.counter25.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("counters")
data class Counter(
    val name: String,
    val value: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
) {
    fun isDecrementable() = value > 0

    fun increment(): Counter {
        return this.copy(value = value + 1)
    }

    fun decrement(): Counter {
        return if (isDecrementable()) {
            this.copy(value = value - 1)
        } else {
            this
        }
    }

    fun reset(): Counter {
        return this.copy(value = 0)
    }

    fun rename(newName: String): Counter {
        return this.copy(name = newName)
    }
}
