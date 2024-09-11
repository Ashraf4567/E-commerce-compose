package com.example.e_commerce_compose.data.model.address

import com.google.gson.annotations.SerializedName

data class AddressDto(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("details")
	val details: String? = null,

	@field:SerializedName("_id")
	val id: String? = null
)
