package com.example.domain.di

import com.example.domain.usecase.GetAllTasksUseCase
import com.example.domain.usecase.InsertTaskUseCase
import com.example.domain.usecase.TaskDelete
import com.example.domain.usecase.UpdateTaskUseCase
import com.example.domain.usecase.GetTaskUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule: Module = module {
    factory { GetAllTasksUseCase(get()) }
    factory { InsertTaskUseCase(get()) }
    factory { UpdateTaskUseCase(get()) }
    factory { TaskDelete(get()) }
    factory { GetTaskUseCase(get()) }
}
