package com.dicoding.restaurantfinder.presentation.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.data.common.PostReview
import com.dicoding.core.data.source.remote.ResponseState
import com.dicoding.core.domain.model.CustomerReviewsItemModel
import com.dicoding.core.domain.model.DetailRestaurantModel
import com.dicoding.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val restaurantUseCase: RestaurantUseCase
) : ViewModel() {
    private val _restaurant = MutableStateFlow(ResponseState<DetailRestaurantModel>())
    val restaurant:StateFlow<ResponseState<DetailRestaurantModel>> = _restaurant

    private val _reviewState = MutableStateFlow(ResponseState<List<CustomerReviewsItemModel>>())
    val reviewState:StateFlow<ResponseState<List<CustomerReviewsItemModel>>> = _reviewState

    fun detailRestaurant(restaurantId: String) {
        viewModelScope.launch {
            try {
                val response = restaurantUseCase.getDetailRestaurant(restaurantId)
                _restaurant.value = _restaurant.value.copy(
                    loading = false,
                    data = response,
                    error = null
                )
                _reviewState.value = _reviewState.value.copy(
                    loading = false,
                    data = response.customerReviews,
                    error = null
                )
            }catch (e: Exception) {
                _restaurant.value = _restaurant.value.copy(
                    loading = false,
                    data = null,
                    error = e.localizedMessage
                )
            }
        }
    }

    fun postReviewRestaurant(restaurantId: String, name: String, review: String) {
        viewModelScope.launch {
            try {
                val request = PostReview(id = restaurantId, name = name, review = review)
                val response = restaurantUseCase.postReview(request)
                _reviewState.value = _reviewState.value.copy(
                    loading = false,
                    data = response,
                    error = null
                )
            }catch (e: Exception) {
                _reviewState.value = _reviewState.value.copy(
                    loading = false,
                    data = null,
                    error = e.localizedMessage
                )
            }
        }
    }
}