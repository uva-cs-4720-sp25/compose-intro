package edu.virginia.cs.counter25.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {
   @Query("SELECT * FROM counters WHERE id=:id")
   fun getCounterById(id: Long): Flow<Counter>

   @Query("SELECT * FROM counters")
   fun getAll(): Flow<List<Counter>>

   @Insert
   suspend fun addCounter(counter: Counter)

   @Update
   suspend fun updateCounter(counter: Counter)

   @Delete
   suspend fun deleteCounter(counter: Counter)
}