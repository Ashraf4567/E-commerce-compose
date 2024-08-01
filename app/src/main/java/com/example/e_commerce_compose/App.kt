package com.example.e_commerce_compose

import android.app.Application
import com.example.e_commerce_compose.di.apiModule
import com.example.e_commerce_compose.di.repositoriesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                listOf(apiModule , repositoriesModule)
            )
        }
    }

}