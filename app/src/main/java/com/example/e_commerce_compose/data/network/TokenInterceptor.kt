package com.example.e_commerce_compose.data.network


import com.example.e_commerce_compose.data.local.DataStoreManager
import com.example.e_commerce_compose.domain.repository.AuthRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val dataStoreManager: DataStoreManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStoreManager.getUserFromDataStore().firstOrNull()?.token }
        val originalRequest = chain.request()
        val requestWithToken = originalRequest.newBuilder()
            .addHeader("token", token?:"") // Add your token header here
            .build()

        return chain.proceed(requestWithToken)
    }
}