package com.example.e_commerce_compose.data.repositoryImpl


import android.util.Log
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.data.safeApiCall
import com.example.e_commerce_compose.data.toDomain
import com.example.e_commerce_compose.data.toDomainModel
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.SubCategory
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(private val webServices: WebServices): CategoriesRepository {
    private val categoriesCache = MutableStateFlow<Resource<List<Category?>?>>(Resource.Loading)

    override suspend fun getCategories(): Flow<Resource<List<Category?>?>> {
        if (categoriesCache.value is Resource.Success) {
            Log.d("test get categories from cache", "getCategories: ${categoriesCache.value}")
            return categoriesCache
        }
        return safeApiCall { webServices.getAllCategories() }.map {
            val result = when (it) {
                is Resource.Error -> it
                is Resource.Loading -> it
                is Resource.ServerError -> it
                is Resource.Success -> Resource.Success(it.data?.map { res -> res?.toDomainModel() })
            }
            categoriesCache.value = result
            result
        }
    }

    override suspend fun getSubCategories(categoryId: String): Flow<Resource<List<SubCategory?>?>> {
        return safeApiCall { webServices.getSubCategories(categoryId) }.map { response->
            when(response){
                is Resource.Error -> response
                is Resource.Loading -> response
                is Resource.ServerError -> response
                is Resource.Success -> {
                    Resource.Success(response.data?.map { res-> res?.toDomain() })
                }
            }
        }
    }
}