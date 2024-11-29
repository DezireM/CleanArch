package com.geeks.cleanArch.presentation.di

import TasksViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val uiModule: Module = module {

    factory { TasksViewModel(get(), get(), get(), get(), get()) }
}
