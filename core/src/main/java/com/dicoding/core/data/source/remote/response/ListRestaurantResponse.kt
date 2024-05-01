package com.dicoding.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListRestaurantResponse(

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class RestaurantsItem(

	@field:SerializedName("pictureId")
	val pictureId: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String
)
