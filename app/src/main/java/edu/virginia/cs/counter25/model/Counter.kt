package edu.virginia.cs.counter25.model

data class Counter(
    val name: String,
    val value: Int = 0
) {
    fun isDecrementable() = value > 0
}
