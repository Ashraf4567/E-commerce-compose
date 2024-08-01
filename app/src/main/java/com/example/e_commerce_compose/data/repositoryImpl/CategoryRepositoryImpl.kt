package com.example.e_commerce_compose.data.repositoryImpl

import android.util.Log
import com.example.e_commerce_compose.data.model.categories.CategoryDto
import com.example.e_commerce_compose.data.model.categories.SubCategoryDto
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.data.safeApiCall
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.SubCategory
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl(private val webServices: WebServices): CategoriesRepository {
    override suspend fun getCategories(): Flow<Resource<List<CategoryDto?>?>>{
        return safeApiCall { webServices.getAllCategories() }
    }

    override suspend fun getSubCategories(categoryId: String): Flow<Resource<List<SubCategoryDto?>?>> {
        return safeApiCall { webServices.getSubCategories(categoryId) }
    }
}