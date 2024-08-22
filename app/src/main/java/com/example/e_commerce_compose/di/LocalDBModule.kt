package com.example.e_commerce_compose.di

import androidx.room.Room
import com.example.e_commerce_compose.data.local.AppDataBase
import com.example.e_commerce_compose.data.local.DataStoreManager
import org.koin.dsl.module


val localDBModule = module {
    //provide application context


    single { DataStoreManager(get()) }

    //provide Room database

    single {
        Room.databaseBuilder(
            get(),
            AppDataBase::class.java,
            "e_commerce_db"
        ).build()
    }

    single { get<AppDataBase>().wishlistDao() }
    single { get<AppDataBase>().cartDao() }

}