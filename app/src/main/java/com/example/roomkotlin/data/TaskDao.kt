package com.example.roomkotlin.data

import androidx.room.*
import com.example.roomkotlin.model.Task
import kotlinx.coroutines.flow.Flow
@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getAll(): Flow<List<Task>>
    @Insert
    suspend fun insert(task: Task)
    @Delete
    suspend fun delete(task: Task)
}