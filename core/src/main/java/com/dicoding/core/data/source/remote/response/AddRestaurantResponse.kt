package com.dicoding.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AddRestaurantResponse(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

