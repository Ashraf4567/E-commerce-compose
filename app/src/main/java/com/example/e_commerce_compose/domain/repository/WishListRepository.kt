package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.data.model.AddToWishlistRequest
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WishListRepository {
    suspend fun addProductToWishList(addToWishlistRequest: AddToWishlistRequest): Flow<Resource<List<String?>?>>
    suspend fun getWishlist(): Flow<Resource<List<Product?>?>>
    suspend fun removeProductFromWishList(productId: String): Flow<Resource<List<String?>?>>

}