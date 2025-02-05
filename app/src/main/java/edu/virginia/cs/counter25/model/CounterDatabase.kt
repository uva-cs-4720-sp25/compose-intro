package edu.virginia.cs.counter25.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Counter::class],
    version = 1
)
abstract class CounterDatabase: RoomDatabase() {
    abstract fun counterDao(): CounterDao
}