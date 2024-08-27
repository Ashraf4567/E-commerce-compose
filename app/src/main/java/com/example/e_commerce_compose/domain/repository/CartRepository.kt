package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.data.model.cart.CartResponse
import com.example.e_commerce_compose.domain.model.Cart
import com.example.e_commerce_compose.domain.model.CartOperationResponse
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun getCart(): Flow<Resource<CartResponse>>
    suspend fun removeProductFromCart(productId: String): Flow<Resource<CartResponse?>>
    suspend fun updateProductCount(productId: String, count: Int): Flow<Resource<CartResponse?>>
    suspend fun addProductToCart(productId: String): Flow<Resource<CartOperationResponse?>>

}