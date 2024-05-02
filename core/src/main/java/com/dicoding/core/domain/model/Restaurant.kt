package com.dicoding.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantsItemModel(
    val pictureId: String,
    val city: String,
    val name: String,
    val rating: String,
    val description: String,
    val id: String
) : Parcelable


@Parcelize
data class DetailRestaurantModel(
    val customerReviews: List<CustomerReviewsItemModel>,
    val address: String,
    val pictureId: String,
    val city: String,
    val name: String,
    val rating: String,
    val description: String,
    val id: String
) : Parcelable

@Parcelize
data class CustomerReviewsItemModel(
    val date: String,
    val review: String,
    val name: String
):Parcelable
