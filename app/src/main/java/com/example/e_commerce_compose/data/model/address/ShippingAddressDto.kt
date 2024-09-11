package com.example.e_commerce_compose.data.model.address

data class ShippingAddressDto(
    val details: String,
    val phone: String,
    val city: String
)

data class ShippingAddressRequest(
    val shippingAddress: ShippingAddressDto
)
