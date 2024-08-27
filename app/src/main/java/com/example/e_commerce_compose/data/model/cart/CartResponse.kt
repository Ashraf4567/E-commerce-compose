package com.example.e_commerce_compose.data.model.cart

import com.google.gson.annotations.SerializedName

data class CartResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("numOfCartItems")
	val numOfCartItems: Int? = null,

	@field:SerializedName("cartId")
	val cartId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ProductsItem(

	@field:SerializedName("product")
	val product: Product? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null
) {
    fun mapToDomain(): com.example.e_commerce_compose.domain.model.Product {
        return com.example.e_commerce_compose.domain.model.Product(
			id = product?.id,
			imageCover = product?.imageCover,
			price = price,
			count = count,
			title = product?.title
		)
    }
}

data class Brand(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class SubcategoryItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class Product(

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("imageCover")
	val imageCover: String? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("subcategory")
	val subcategory: List<SubcategoryItem?>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("brand")
	val brand: Brand? = null,

	@field:SerializedName("ratingsAverage")
	val ratingsAverage: Double? = null
)

data class Data(

	@field:SerializedName("cartOwner")
	val cartOwner: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("totalCartPrice")
	val totalCartPrice: Int? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Category(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)
