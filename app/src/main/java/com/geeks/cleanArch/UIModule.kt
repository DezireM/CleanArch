package com.geeks.cleanArch

import MainActivityViewModel
import com.example.hw_4.domain.usecase.InsertTaskUseCase
import com.geeks.cleanArch.domain.usecase.GetAllTasksUseCase
import com.geeks.cleanArch.domain.usecase.GetTaskUseCase
import com.geeks.cleanArch.domain.usecase.TaskDelete
import com.geeks.cleanArch.domain.usecase.UpdateTaskUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val uiModule: Module = module {

    factory { MainActivityViewModel(get(),get(),get(),get(),get()) }
    single { InsertTaskUseCase(get()) }
    single { GetAllTasksUseCase(get()) }
    single { UpdateTaskUseCase(get()) }
    single { TaskDelete(get()) }
    single { GetTaskUseCase(get()) }

}