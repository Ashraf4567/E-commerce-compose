package com.example.e_commerce_compose.di

import android.util.Log
import com.example.e_commerce_compose.data.network.WebServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single<HttpLoggingInterceptor> {
        val loggingInterceptor = HttpLoggingInterceptor{message -> Log.d("Api Logging", message)}
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        loggingInterceptor
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://ecommerce.routemisr.com/")
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single<WebServices> {
        get<Retrofit>().create(WebServices::class.java)
    }
}