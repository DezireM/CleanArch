package com.geeks.cleanArch.presentation.di



import com.geeks.cleanArch.presentation.fragment.viewmodel.TaskViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val uiModule: Module = module {

    factory { TaskViewModel(get(), get(), get(), get(), get()) }
}