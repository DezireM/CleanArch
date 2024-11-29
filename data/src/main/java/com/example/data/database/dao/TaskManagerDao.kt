package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.data.dto.TaskDto
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskManagerDao {

    @Insert
    suspend fun insertTask(task: TaskDto)

    @Update
    suspend fun updateTask(task: TaskDto)

    @Delete
    suspend fun deleteTask(task: TaskDto)

    @Query("SELECT * FROM TaskDto WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskDto?

    @Query("SELECT * FROM TaskDto WHERE taskName = :taskName LIMIT 1")
    suspend fun getTaskByName(taskName: String): TaskDto?

    @Query("SELECT * FROM TaskDto")
    fun getAllTasks(): Flow<List<TaskDto>>
}
