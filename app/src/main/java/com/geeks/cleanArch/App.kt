package com.geeks.cleanArch

import addTaskModule
import android.app.Application
import com.example.data.database.di.dataModules
import com.example.domain.di.domainModule
import com.geeks.cleanArch.presentation.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin



class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(dataModules)
            modules(domainModule)
            modules(uiModule)
            modules(addTaskModule)
        }
    }
}