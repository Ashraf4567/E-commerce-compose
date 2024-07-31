package com.example.e_commerce_compose.data.network

import com.example.e_commerce_compose.data.repositoryImpl.CategoriesResponse
import retrofit2.http.GET

interface WebServices {

    @GET("api/v1/categories")
    suspend fun getAllCategories(): CategoriesResponse
}