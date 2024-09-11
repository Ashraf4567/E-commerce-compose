package com.example.e_commerce_compose.data.repositoryImpl

import com.example.e_commerce_compose.data.local.cart.CartDao
import com.example.e_commerce_compose.data.local.cart.CartEntity
import com.example.e_commerce_compose.data.model.cart.CartResponse
import com.example.e_commerce_compose.data.model.cart.UpdateQuantityRequest
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.data.safeApiCall
import com.example.e_commerce_compose.domain.model.AddToCartRequest
import com.example.e_commerce_compose.domain.model.CartOperationResponse
import com.example.e_commerce_compose.domain.repository.CartRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class CartRepositoryImpl(private val webServices: WebServices , private val cartDao: CartDao): CartRepository {

    override suspend fun getCart(): Flow<Resource<CartResponse>> = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(webServices.getCart() , null))
        }catch (e: Exception){
            emit(Resource.Error(Exception(e.message)))
        }

    }

    override suspend fun removeProductFromCart(productId: String): Flow<Resource<CartResponse?>> = flow {
        emit(Resource.Loading)
        try {
            cartDao.deleteById(productId)
            emit(Resource.Success(webServices.removeProductFromCart(productId) , null))

        }catch (e: Exception){
            emit(Resource.Error(Exception(e.message)))
        }
    }

    override suspend fun updateProductCount(
        productId: String,
        count: Int
    ): Flow<Resource<CartResponse?>> = flow {
        emit(Resource.Loading)
        try {
            val success = Resource.Success(webServices.updateProductCountInCart(
                productId = productId,
                updateQuantityRequest = UpdateQuantityRequest(count)
            ),null)
            emit(success)

        }catch (e: Exception){
            emit(Resource.Error(Exception(e.message)))
        }
    }.catch {
        emit(Resource.Error(Exception(it.message)))
    }

    override suspend fun addProductToCart(productId: String): Flow<Resource<CartOperationResponse?>> {
        cartDao.insert(
            CartEntity(
                id = productId
            )
        )
        return safeApiCall {
            webServices.addProductToCart(
                AddToCartRequest(productId)
            )
        }
    }


}