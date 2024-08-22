package com.example.e_commerce_compose.data.repositoryImpl

import com.example.e_commerce_compose.data.local.wishlist.WishlistDao
import com.example.e_commerce_compose.data.local.wishlist.WishlistEntity
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.data.safeApiCall
import com.example.e_commerce_compose.data.toDomain
import com.example.e_commerce_compose.domain.model.AddToWishlistRequest
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductsRepositoryImpl(
    private val webServices: WebServices,
    private val wishlistDao: WishlistDao
): ProductsRepository {

    override suspend fun getProductsByCategoryId(categoryId: String): Flow<Resource<List<Product?>?>> {
        return safeApiCall { webServices.getProductsByCategory(categoryId) }.map {resource->
            when(resource){
                is Resource.Error -> resource
                is Resource.Loading -> resource
                is Resource.ServerError -> resource
                is Resource.Success -> {
                    val favoriteIds = wishlistDao.getAllIds()
                    Resource.Success(
                        resource.data?.map {dto->
                        dto?.toDomain()?.copy(isFavorite = favoriteIds.contains(dto.id))
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
                    val favoriteIds = wishlistDao.getAllIds()
                    Resource.Success(
                        resource.data?.toDomain()?.copy(isFavorite = favoriteIds.contains(resource.data.id)),
                        resource.metadata
                    )

                }
            }

        }
    }

    override suspend fun addProductToWishList(addToWishlistRequest: AddToWishlistRequest): Flow<Resource<List<String?>?>>{
        wishlistDao.insert(WishlistEntity(
            id = addToWishlistRequest.productId
        ))
        return safeApiCall { webServices.addProductToWishList(addWishListRequest = addToWishlistRequest) }
    }

    override suspend fun getWishlist(): Flow<Resource<List<Product?>?>> {
        return safeApiCall { webServices.getWishlist() }.map {resource->
            when(resource){
                is Resource.Error -> resource
                is Resource.Loading -> resource
                is Resource.ServerError -> resource
                is Resource.Success -> {
                    Resource.Success(
                        resource.data?.map {dto->
                            dto?.toDomain()?.copy(isFavorite = true)
                        },
                        resource.metadata
                    )
                }
            }

        }
    }

    override suspend fun removeProductFromWishList(productId: String): Flow<Resource<List<String?>?>> {
        wishlistDao.delete(productId)
        return safeApiCall { webServices.removeProductFromWishlist(productId) }
    }
}