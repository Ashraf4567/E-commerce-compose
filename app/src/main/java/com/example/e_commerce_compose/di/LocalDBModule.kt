package com.example.e_commerce_compose.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDBModule = module {
    //provide application context

    single {
        androidContext()
    }
}