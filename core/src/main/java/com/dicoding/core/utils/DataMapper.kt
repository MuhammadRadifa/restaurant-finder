package com.dicoding.core.utils

import com.dicoding.core.data.source.local.room.FavoriteEntity
import com.dicoding.core.data.source.remote.response.CustomerReviewsItem
import com.dicoding.core.data.source.remote.response.Restaurant
import com.dicoding.core.data.source.remote.response.RestaurantsItem
import com.dicoding.core.domain.model.CustomerReviewsItemModel
import com.dicoding.core.domain.model.DetailRestaurantModel
import com.dicoding.core.domain.model.FavoriteModel
import com.dicoding.core.domain.model.RestaurantsItemModel

object DataMapper {
    fun restaurantResponseToDomain(input: List<RestaurantsItem>): List<RestaurantsItemModel> =
        input.map {
            RestaurantsItemModel(
                pictureId = it.pictureId,
                city = it.city,
                name = it.name,
                rating = it.rating.toString(),
                description = it.description,
                id = it.id
            )
        }

    fun detailResponseToDomain(input: Restaurant): DetailRestaurantModel =
        DetailRestaurantModel(
            pictureId = input.pictureId,
            city = input.city,
            name = input.name,
            rating = input.rating,
            description = input.description,
            id = input.id,
            address = input.address,
            customerReviews = reviewResponseToDomain(input.customerReviews)
        )

    fun reviewResponseToDomain(input: List<CustomerReviewsItem>): List<CustomerReviewsItemModel> =
        input.map {
            CustomerReviewsItemModel(
                date = it.date,
                review = it.review,
                name = it.name
            )
        }

    fun favoriteDomainToEntity(input:FavoriteModel):FavoriteEntity = FavoriteEntity(
        id = input.id,
        pictureId = input.pictureId,
        city = input.city,
        name = input.name,
        rating = input.rating,
        description = input.description
    )

    fun favoriteEntityToDomain(input: FavoriteEntity): FavoriteModel =
        FavoriteModel(
            id = input.id,
            pictureId = input.pictureId,
            city = input.city,
            name = input.name,
            rating = input.rating,
            description = input.description
        )

    fun favoriteEntitiesToDomain(input: List<FavoriteEntity>): List<FavoriteModel> =
        input.map {
            FavoriteModel(
                id = it.id,
                pictureId = it.pictureId,
                city = it.city,
                name = it.name,
                rating = it.rating,
                description = it.description
            )
        }

}