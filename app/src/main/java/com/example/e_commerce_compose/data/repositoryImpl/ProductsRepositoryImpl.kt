package com.example.e_commerce_compose.data.repositoryImpl

import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.data.safeApiCall
import com.example.e_commerce_compose.data.toDomain
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductsRepositoryImpl(private val webServices: WebServices): ProductsRepository {

    override suspend fun getProductsByCategoryId(categoryId: String): Flow<Resource<List<Product?>?>> {
        return safeApiCall { webServices.getProductsByCategory(categoryId) }.map {resource->
            when(resource){
                is Resource.Error -> resource
                is Resource.Loading -> resource
                is Resource.ServerError -> resource
                is Resource.Success -> {
                    Resource.Success(
                        resource.data?.map {
                        it?.toDomain()
                        },
                        resource.metadata
                    )
                }
            }

        }
    }

    override suspend fun getProductDetailsById(productId: String): Flow<Resource<Product?>> {
        return safeApiCall { webServices.getProductDetails(productId) }.map {resource->
            when(resource){
                is Resource.Error -> resource
                is Resource.Loading -> resource
                is Resource.ServerError -> resource
                is Resource.Success -> {
                    Resource.Success(
                        resource.data?.toDomain(),
                        resource.metadata
                    )

                }
            }

        }
    }
}