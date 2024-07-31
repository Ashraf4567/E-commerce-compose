package com.example.e_commerce_compose.data.network


import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

//class TokenInterceptor() : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
////        val token = sessionManager.getUserData()?.token.toString()
//        val originalRequest = chain.request()
//        val requestWithToken = originalRequest.newBuilder()
//            .addHeader("token", token) // Add your token header here
//            .build()
//
//        return chain.proceed(requestWithToken)
//    }
//}