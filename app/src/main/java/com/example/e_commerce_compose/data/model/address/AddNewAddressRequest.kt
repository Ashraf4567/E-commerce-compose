package com.example.e_commerce_compose.data.model.address

data class AddNewAddressRequest(
    val name: String? = null,
    val details: String? = null,
    val phone: String? = null,
    val city: String? = null,
)
