package com.dicoding.core.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.domain.model.FavoriteModel
import com.dicoding.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val restaurantUseCase: RestaurantUseCase
) :ViewModel(){
    fun addFavorite(favoriteEntity: FavoriteModel){
        viewModelScope.launch {
            restaurantUseCase.addFavorite(favoriteEntity)
        }
    }

    fun deleteFavorite(favoriteEntity: FavoriteModel){
        viewModelScope.launch {
            restaurantUseCase.deleteFavoriteByName(favoriteEntity)
        }
    }

    fun getAllFavorites(): Flow<List<FavoriteModel>> = restaurantUseCase.getAllFavorites()

    fun getFavoriteByName(id: String):Flow<FavoriteModel> = restaurantUseCase.getFavoriteById(id)

}