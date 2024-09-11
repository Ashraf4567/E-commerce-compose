package com.example.e_commerce_compose.data.model.order

import com.google.gson.annotations.SerializedName



data class CreateCashOrderResponse(

    @field:SerializedName("totalOrderPrice")
	val totalOrderPrice: Int? = null,

    @field:SerializedName("isPaid")
	val isPaid: Boolean? = null,

    @field:SerializedName("isDelivered")
	val isDelivered: Boolean? = null,

    @field:SerializedName("createdAt")
	val createdAt: String? = null,

    @field:SerializedName("shippingPrice")
	val shippingPrice: Int? = null,

    @field:SerializedName("__v")
	val V: Int? = null,

    @field:SerializedName("taxPrice")
	val taxPrice: Int? = null,

    @field:SerializedName("shippingAddress")
	val shippingAddress: ShippingAddress? = null,

    @field:SerializedName("_id")
	val _id: String? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("cartItems")
	val cartItems: List<CartItemsItem?>? = null,

    @field:SerializedName("paymentMethodType")
	val paymentMethodType: String? = null,

    @field:SerializedName("user")
	val user: String? = null,

    @field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ShippingAddress(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("details")
	val details: String? = null
)

data class CartItemsItem(

	@field:SerializedName("product")
	val product: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null
)
