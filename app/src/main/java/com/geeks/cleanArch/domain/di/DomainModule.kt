package com.geeks.cleanArch.domain.di

import com.geeks.cleanArch.domain.usecase.InsertTaskUseCase
import com.geeks.cleanArch.domain.usecase.UseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule: Module = module {

    factory { InsertTaskUseCase(get()) }
    factory { UseCase(get()) }
}