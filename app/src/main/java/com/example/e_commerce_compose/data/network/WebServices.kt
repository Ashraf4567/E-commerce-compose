package com.example.e_commerce_compose.data.network

import com.example.e_commerce_compose.data.model.BaseResponse
import com.example.e_commerce_compose.data.model.categories.CategoryDto
import com.example.e_commerce_compose.data.model.categories.SubCategoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface WebServices {

    @GET("api/v1/categories")
    suspend fun getAllCategories(): BaseResponse<List<CategoryDto?>?>

    @GET("api/v1/categories/{id}/subcategories")
    suspend fun getSubCategories(@Path("id")categoryId: String): BaseResponse<List<SubCategoryDto?>?>
}