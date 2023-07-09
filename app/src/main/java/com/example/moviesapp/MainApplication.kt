package com.example.moviesapp

import android.app.Application
import com.example.data.di.dataModule
import com.example.moviesapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule, dataModule)
        }
    }
}
