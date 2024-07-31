package com.example.e_commerce_compose.data.repositoryImpl

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(

	@field:SerializedName("metadata")
	val metadata: Metadata? = null,

	@field:SerializedName("data")
	val data: List<CategoryDto?>? = null,

	@field:SerializedName("results")
	val results: Int? = null
)

data class Metadata(

	@field:SerializedName("numberOfPages")
	val numberOfPages: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("currentPage")
	val currentPage: Int? = null
)
