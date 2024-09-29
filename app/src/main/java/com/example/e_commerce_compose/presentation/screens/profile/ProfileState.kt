package com.example.e_commerce_compose.presentation.screens.profile

import com.example.e_commerce_compose.data.model.order.UserOrdersResponseItem
import com.example.e_commerce_compose.domain.model.Address

data class ProfileState(
    val isLoading: Boolean = false,
    val addresses: List<Address> = emptyList(),
    val error: String? = null,
    val showAddAddressDialog: Boolean = false,
    val addressToAdd: Address = Address(),
    val userOrders: Map<String?, List<UserOrdersResponseItem>> = emptyMap()
)
