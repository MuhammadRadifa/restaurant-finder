package com.dicoding.core.data.source

import android.util.Log
import com.dicoding.core.data.common.PostReview
import com.dicoding.core.data.source.local.LocalDataSource
import com.dicoding.core.data.source.remote.RemoteDataSource
import com.dicoding.core.domain.model.CustomerReviewsItemModel
import com.dicoding.core.domain.model.DetailRestaurantModel
import com.dicoding.core.domain.model.FavoriteModel
import com.dicoding.core.domain.model.RestaurantsItemModel
import com.dicoding.core.domain.repository.IRestaurantRepository
import com.dicoding.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RestaurantRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IRestaurantRepository {
    override suspend fun getAllRestaurant(): List<RestaurantsItemModel> {
        return remoteDataSource.getAllRestaurant().let {
            DataMapper.restaurantResponseToDomain(it)
        }
    }

    override suspend fun getDetailRestaurant(id: String): DetailRestaurantModel {
        return remoteDataSource.getDetailRestaurant(id).let {
            DataMapper.detailResponseToDomain(it)
        }
    }

    override suspend fun searchRestaurant(query: String): List<RestaurantsItemModel> {
        return remoteDataSource.searchRestaurant(query).let {
            DataMapper.restaurantResponseToDomain(it)
        }
    }

    override suspend fun postReview(body: PostReview): List<CustomerReviewsItemModel> {
        return remoteDataSource.postReview(body).let {
            DataMapper.reviewResponseToDomain(it)
        }
    }

    override suspend fun saveUsername(token: String) {
        localDataSource.saveUsername(token)
    }

    override fun getUsername(): Flow<String?> {
        return localDataSource.getUsername()
    }

    override suspend fun addFavorite(entity: FavoriteModel) {
        localDataSource.addFavorite(DataMapper.favoriteDomainToEntity(entity))
    }

    override fun getAllFavorites(): Flow<List<FavoriteModel>> = localDataSource.getAllFavorites().map {
        DataMapper.favoriteEntitiesToDomain(it)
    }

    override fun getFavoriteById(id: String): Flow<FavoriteModel> = localDataSource.getFavoriteById(id).map {
        favoriteEntity ->
        if (favoriteEntity != null) {
            Log.e("getFavoriteById", favoriteEntity.toString())
            DataMapper.favoriteEntityToDomain(favoriteEntity)
        } else {
            Log.e("getFavoriteById", "null")
            FavoriteModel(id = "", pictureId = "", city = "", name = "", rating = "", description = "")
        }

    }

    override suspend fun deleteFavoriteByName(entity: FavoriteModel) {
        localDataSource.deleteFavoriteByName(DataMapper.favoriteDomainToEntity(entity))
    }
}