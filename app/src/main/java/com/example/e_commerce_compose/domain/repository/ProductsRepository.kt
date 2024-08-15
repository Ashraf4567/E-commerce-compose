package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProductsByCategoryId(categoryId: String): Flow<Resource<List<Product?>?>>
    suspend fun getProductDetailsById(productId: String): Flow<Resource<Product?>>
}