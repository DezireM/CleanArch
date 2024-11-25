package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.TaskManagerDao
import com.example.data.dto.TaskDto

@Database(entities = [TaskDto::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskManagerDao(): TaskManagerDao
}