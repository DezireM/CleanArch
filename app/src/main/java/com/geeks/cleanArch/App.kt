package com.geeks.cleanArch

import android.app.Application
import com.geeks.cleanArch.data.database.di.dataModules
import com.geeks.cleanArch.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(dataModules)
            modules(domainModule)
            modules(uiModule)
        }
    }
}