package com.dicoding.restaurantfinder.presentation.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.data.source.remote.ResponseState
import com.dicoding.core.domain.model.RestaurantsItemModel
import com.dicoding.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val restaurantUseCase: RestaurantUseCase
) : ViewModel() {
    private val _restaurant = MutableStateFlow(ResponseState<List<RestaurantsItemModel>>())
    val restaurant:StateFlow<ResponseState<List<RestaurantsItemModel>>> = _restaurant

    init {
        getAllRestaurant()
    }

    private fun getAllRestaurant() {
        viewModelScope.launch {
            try {
                val response = restaurantUseCase.getAllRestaurant()
                _restaurant.value = _restaurant.value.copy(
                    data = response,
                    loading = false,
                    error = null
                )

            }catch (e: Exception){
                Log.e("HomeViewModel", "getAllRestaurant: $e")
                _restaurant.value = _restaurant.value.copy(
                    data = null,
                    loading = false,
                    error = e.message
                )
            }
        }
    }

}