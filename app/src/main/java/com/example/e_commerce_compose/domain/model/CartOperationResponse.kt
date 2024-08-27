package com.example.e_commerce_compose.domain.model

data class CartOperationResponse(
	val data: Data? = null,
	val numOfCartItems: Int? = null,
	val cartId: String? = null,
	val message: String? = null,
	val status: String? = null
)

data class ProductsItem(
	val product: String? = null,
	val price: Int? = null,
	val count: Int? = null,
	val id: String? = null
)

data class Data(
	val cartOwner: String? = null,
	val createdAt: String? = null,
	val totalCartPrice: Int? = null,
	val __v: Int? = null,
	val id: String? = null,
	val products: List<ProductsItem?>? = null,
	val updatedAt: String? = null
)

