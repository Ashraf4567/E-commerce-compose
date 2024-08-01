package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.SubCategory
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getCategories(): Flow<Resource<List<Category?>?>>
//    fun getSubCategories(categoryId: Int): Flow<Resource<List<SubCategory?>?>>

}