package com.example.e_commerce_compose.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.e_commerce_compose.data.network.WebServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {


    @Provides
    fun provideLoggingInterceptor():HttpLoggingInterceptor{
        val loggingInterceptor = HttpLoggingInterceptor {
                message -> Log.e("api", message ?:"") }

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context):SharedPreferences{
        return context.getSharedPreferences("user_data",0)
    }

//    @Provides
//    fun provideSessionManger(sharedPreferences: SharedPreferences): SessionManagerImpl{
//        return SessionManagerImpl(sharedPreferences)
//    }
//
//    @Provides
//    fun provideTokenInterceptor(sessionManagerImpl: SessionManagerImpl): TokenInterceptor {
//        return TokenInterceptor(sessionManagerImpl)
//    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
//        tokenInterceptor: TokenInterceptor
    ):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    fun provideGsonConvertorFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }


        @Provides
        fun provideRetrofit(client: OkHttpClient,
        converter:GsonConverterFactory):Retrofit{
            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://ecommerce.routemisr.com/")
                .addConverterFactory(converter)
                .build()
        }
    @Provides
    fun provideWebServices(retrofit: Retrofit): WebServices {
        return retrofit.create(WebServices::class.java)
    }
}