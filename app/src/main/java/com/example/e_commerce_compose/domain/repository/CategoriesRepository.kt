package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.data.model.categories.CategoryDto
import com.example.e_commerce_compose.data.model.categories.SubCategoryDto
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.SubCategory
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun getCategories(): Flow<Resource<List<CategoryDto?>?>>
    suspend fun getSubCategories(categoryId: String): Flow<Resource<List<SubCategoryDto?>?>>

}