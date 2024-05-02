package com.dicoding.restaurantfinder.presentation.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.data.source.remote.ResponseState
import com.dicoding.core.domain.model.RestaurantsItemModel
import com.dicoding.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val restaurantUseCase: RestaurantUseCase
) :ViewModel(){
    private val _search = MutableStateFlow(ResponseState<List<RestaurantsItemModel>>())
    val search:StateFlow<ResponseState<List<RestaurantsItemModel>>> = _search

    fun searchRestaurant(query: String){
        viewModelScope.launch {
            try {
                val response = restaurantUseCase.searchRestaurant(query)
                _search.value = _search.value.copy(
                    loading = false,
                    data = response,
                    error = null
                )
            }catch (e: Exception){
                _search.value = _search.value.copy(
                    loading = false,
                    data = emptyList(),
                    error = e.localizedMessage
                )
            }
        }
    }
}