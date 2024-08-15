package com.example.e_commerce_compose.data.network

import com.example.e_commerce_compose.data.model.BaseResponse
import com.example.e_commerce_compose.data.model.categories.CategoryDto
import com.example.e_commerce_compose.data.model.categories.SubCategoryDto
import com.example.e_commerce_compose.data.model.products.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServices {

    @GET("api/v1/categories")
    suspend fun getAllCategories(): BaseResponse<List<CategoryDto?>?>

    @GET("api/v1/categories/{id}/subcategories")
    suspend fun getSubCategories(@Path("id")categoryId: String): BaseResponse<List<SubCategoryDto?>?>

    @GET("api/v1/products")
    suspend fun getProductsByCategory(
        @Query("category") categoryId: String,
        @Query("sort") sort: String = "price",
        @Query("limit") limit: Int = 10
    ): BaseResponse<List<ProductDto?>?>

    @GET("api/v1/products/{id}")
    suspend fun getProductDetails(@Path("id") productId: String): BaseResponse<ProductDto?>
}