package com.example.e_commerce_compose.data.repositoryImpl

import android.database.sqlite.SQLiteException
import com.example.e_commerce_compose.data.local.cart.CartDao
import com.example.e_commerce_compose.data.model.order.CreateCashOrderResponse
import com.example.e_commerce_compose.data.model.address.ShippingAddressRequest
import com.example.e_commerce_compose.data.model.order.UserOrdersResponseItem
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.data.safeApiCall
import com.example.e_commerce_compose.domain.repository.OrdersRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class OrdersRepositoryImpl(
    private val webServices: WebServices,
    private val cartDao: CartDao
): OrdersRepository {

    override suspend fun createCashOrder(
        shippingAddressRequest: ShippingAddressRequest,
        cartId: String
    ): Flow<Resource<CreateCashOrderResponse?>> {
        return safeApiCall {
            webServices.createCashOrder(
            shippingAddressRequest = shippingAddressRequest,
            cartId = cartId
            )
        }.map {response->
            when(response){
                is Resource.Error -> response
                Resource.Loading -> response
                is Resource.ServerError -> response
                is Resource.Success -> {
                    try {
                        cartDao.clearTable()
                        response
                    } catch (e: SQLiteException) {
                        Resource.Error(e)
                    }
                }
            }
        }
    }

    override suspend fun getUserOrders(userId: String): Flow<Resource<Map<String?, List<UserOrdersResponseItem>>>> = flow {
        emit(Resource.Loading)
        val response=  webServices.getUserOrders(userId).map {
            it.copy(createdAt = it.createdAt?.substringBefore("T"))
        }.groupBy({it.createdAt},{it})

        emit(Resource.Success(response))
    }.catch {error->
        emit(Resource.Error(Exception(error.message)))

    }
}