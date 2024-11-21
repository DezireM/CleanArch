package com.geeks.cleanArch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geeks.cleanArch.data.database.dao.TaskManagerDao
import com.geeks.cleanArch.data.dto.TaskDto

@Database(entities = [TaskDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskManagerDao(): TaskManagerDao
}