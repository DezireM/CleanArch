package com.geeks.cleanArch.data.database.di

import androidx.room.Room
import com.geeks.cleanArch.data.database.AppDatabase
import com.geeks.cleanArch.data.repositoryImpl.TaskManagerRepositoryImpl
import com.geeks.cleanArch.domain.reposiotry.TaskManagerRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModules: Module = module {

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "TaskDataBase")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().taskManagerDao() }


    single<TaskManagerRepository> { TaskManagerRepositoryImpl(get()) }
}