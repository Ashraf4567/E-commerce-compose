package com.example.e_commerce_compose.data.repositoryImpl

import com.example.e_commerce_compose.data.local.cart.CartDao
import com.example.e_commerce_compose.data.local.wishlist.WishlistDao
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.data.safeApiCall
import com.example.e_commerce_compose.data.toDomain
import com.example.e_commerce_compose.domain.model.Brand
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductsRepositoryImpl(
    private val webServices: WebServices,
    private val wishlistDao: WishlistDao,
    private val cartDao: CartDao
): ProductsRepository {

    override suspend fun getProductsByCategoryId(
        categoryId: String?,
        sort: String?,
        limit: Int?,
        brand: String?,
        minPrice: Int?,
        page: Int?
    ): Flow<Resource<List<Product?>?>> {
        return safeApiCall {
            webServices.getProductsByFilters(
                categoryId = categoryId,
                sort = sort,
                limit = limit,
                brand = brand,
                minPrice = minPrice
            )
        }.map { resource->
            when(resource){
                is Resource.Error -> resource
                is Resource.Loading -> resource
                is Resource.ServerError -> resource
                is Resource.Success -> {
                    val favoriteIds = wishlistDao.getAllIds()
                    val cartIds = cartDao.getAllIds()
                    Resource.Success(
                        resource.data?.map {dto->
                        dto?.toDomain()?.copy(
                            isFavorite = favoriteIds.contains(dto.id),
                            isInCart = cartIds.contains(dto.id)
                        )
                        }
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
                    val favoriteIds = wishlistDao.getAllIds()
                    val cartIds = cartDao.getAllIds()
                    Resource.Success(
                        resource.data?.toDomain()?.copy(
                            isFavorite = favoriteIds.contains(resource.data.id),
                            isInCart = cartIds.contains(resource.data.id)
                        )
                    )

                }
            }

        }
    }


    override suspend fun getAllBrands(): Flow<Resource<List<Brand?>?>> {
        return safeApiCall { webServices.getBrands() }.map {results->
            when(results){
                is Resource.Success -> {
                    Resource.Success(
                        results.data?.map {dto->
                            dto?.toDomain()
                        }
                    )
                }
                is Resource.Error -> results
                is Resource.Loading -> results
                is Resource.ServerError -> results


            }
        }
    }
}