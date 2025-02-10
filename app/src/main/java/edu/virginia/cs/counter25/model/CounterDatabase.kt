package edu.virginia.cs.counter25.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Counter::class],
    version = 1
)
abstract class CounterDatabase: RoomDatabase() {
    abstract fun counterDao(): CounterDao

    companion object {
        @Volatile
        private var instance: CounterDatabase? = null

        fun getDatabase(context: Context): CounterDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, CounterDatabase::class.java, "counters.db")
                    .build()
                    .also { instance = it }
            }
        }
    }
}