package com.example.e_commerce_compose.data.repositoryImpl

import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.SubCategory
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val webServices: WebServices): CategoriesRepository {
    override fun getCategories(): Flow<Resource<List<Category?>?>> = flow {
        emit(Resource.Loading)
        try {
            val response = webServices.getAllCategories()
            val data = response.data?.map { it.toDomainModel() }
            emit(Resource.Success(data))

        }catch (e: Exception){
            emit(Resource.Error(e))
        }
    }

//    override fun getSubCategories(categoryId: Int): Flow<Resource<List<SubCategory?>?>> {
//
//    }
}