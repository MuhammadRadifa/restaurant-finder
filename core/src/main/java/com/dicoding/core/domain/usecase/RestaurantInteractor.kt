package com.dicoding.core.domain.usecase

import com.dicoding.core.data.common.PostReview
import com.dicoding.core.domain.model.CustomerReviewsItemModel
import com.dicoding.core.domain.model.DetailRestaurantModel
import com.dicoding.core.domain.model.FavoriteModel
import com.dicoding.core.domain.model.RestaurantsItemModel
import com.dicoding.core.domain.repository.IRestaurantRepository
import kotlinx.coroutines.flow.Flow

class RestaurantInteractor (private val restaurantRepository: IRestaurantRepository): RestaurantUseCase{
    override suspend fun getAllRestaurant(): List<RestaurantsItemModel> = restaurantRepository.getAllRestaurant()
    override suspend fun getDetailRestaurant(id: String): DetailRestaurantModel = restaurantRepository.getDetailRestaurant(id)
    override suspend fun searchRestaurant(query: String): List<RestaurantsItemModel> = restaurantRepository.searchRestaurant(query)
    override suspend fun postReview(body: PostReview): List<CustomerReviewsItemModel> = restaurantRepository.postReview(body)
    override suspend fun saveUsername(token: String) = restaurantRepository.saveUsername(token)
    override fun getUsername(): Flow<String?> = restaurantRepository.getUsername()
    override suspend fun addFavorite(entity: FavoriteModel) = restaurantRepository.addFavorite(entity)
    override fun getAllFavorites(): Flow<List<FavoriteModel>> = restaurantRepository.getAllFavorites()
    override fun getFavoriteById(id: String): Flow<FavoriteModel> = restaurantRepository.getFavoriteById(id)
    override suspend fun deleteFavoriteByName(entity: FavoriteModel) = restaurantRepository.deleteFavoriteByName(entity)
}