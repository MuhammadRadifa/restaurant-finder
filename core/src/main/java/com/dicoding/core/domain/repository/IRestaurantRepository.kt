package com.dicoding.core.domain.repository

import com.dicoding.core.data.common.PostReview
import com.dicoding.core.domain.model.CustomerReviewsItemModel
import com.dicoding.core.domain.model.DetailRestaurantModel
import com.dicoding.core.domain.model.FavoriteModel
import com.dicoding.core.domain.model.RestaurantsItemModel
import kotlinx.coroutines.flow.Flow


interface IRestaurantRepository {
    suspend fun getAllRestaurant(): List<RestaurantsItemModel>
    suspend fun getDetailRestaurant(id: String): DetailRestaurantModel
    suspend fun searchRestaurant(query: String): List<RestaurantsItemModel>
    suspend fun postReview(body: PostReview): List<CustomerReviewsItemModel>
    suspend fun saveUsername(token: String): Unit
    fun getUsername(): Flow<String?>
    suspend fun addFavorite(entity: FavoriteModel): Unit
    fun getAllFavorites(): Flow<List<FavoriteModel>>
    fun getFavoriteById(id: String): Flow<FavoriteModel>
    suspend fun deleteFavoriteByName(entity: FavoriteModel)
}