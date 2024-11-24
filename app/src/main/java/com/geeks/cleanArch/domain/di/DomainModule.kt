package com.geeks.cleanArch.domain.di

import com.example.hw_4.domain.usecase.InsertTaskUseCase
import com.geeks.cleanArch.domain.usecase.GetAllTasksUseCase
import com.geeks.cleanArch.domain.usecase.GetTaskUseCase
import com.geeks.cleanArch.domain.usecase.TaskDelete
import com.geeks.cleanArch.domain.usecase.UpdateTaskUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule: Module = module {

    factory { InsertTaskUseCase(get()) }
    single { UpdateTaskUseCase(get()) }
    single { GetAllTasksUseCase(get()) }
    single { TaskDelete(get()) }
    single { GetTaskUseCase(get()) }
}