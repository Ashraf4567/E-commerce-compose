package com.example.e_commerce_compose.data.model.order

import com.example.e_commerce_compose.data.model.cart.CartProductsItem
import com.google.gson.annotations.SerializedName


data class UserOrdersResponseItem(

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

	@field:SerializedName("shippingAddress")
	val shippingAddress: ShippingAddress? = null,

	@field:SerializedName("taxPrice")
	val taxPrice: Int? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("cartItems")
	val cartItems: List<CartProductsItem?>? = null,

	@field:SerializedName("paymentMethodType")
	val paymentMethodType: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)


data class User(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)


