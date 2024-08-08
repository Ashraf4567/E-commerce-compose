package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.SubCategory
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun getCategories(): Flow<Resource<List<Category?>?>>
    suspend fun getSubCategories(categoryId: String): Flow<Resource<List<SubCategory?>?>>

}