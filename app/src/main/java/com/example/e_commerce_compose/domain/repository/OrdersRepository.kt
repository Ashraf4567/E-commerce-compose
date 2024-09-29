package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.data.model.order.CreateCashOrderResponse
import com.example.e_commerce_compose.data.model.address.ShippingAddressRequest
import com.example.e_commerce_compose.data.model.order.UserOrdersResponseItem
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {

    suspend fun createCashOrder(
        shippingAddressRequest: ShippingAddressRequest,
        cartId: String
    ): Flow<Resource<CreateCashOrderResponse?>>

    suspend fun getUserOrders(userId: String): Flow<Resource<Map<String?, List<UserOrdersResponseItem>>>>
}