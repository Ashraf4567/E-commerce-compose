package com.example.e_commerce_compose.data.network

import com.example.e_commerce_compose.data.model.BaseResponse
import com.example.e_commerce_compose.data.model.categories.CategoriesResponse
import com.example.e_commerce_compose.data.model.categories.CategoryDto
import com.example.e_commerce_compose.domain.model.Category
import retrofit2.http.GET

interface WebServices {

    @GET("api/v1/categories")
    suspend fun getAllCategories(): BaseResponse<List<CategoryDto>>
}