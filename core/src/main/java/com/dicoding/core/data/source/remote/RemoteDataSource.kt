package com.dicoding.core.data.source.remote

import com.dicoding.core.data.common.PostReview
import com.dicoding.core.data.source.remote.network.ApiService
import com.dicoding.core.data.source.remote.response.AddRestaurantResponse
import com.dicoding.core.data.source.remote.response.CustomerReviewsItem
import com.dicoding.core.data.source.remote.response.DetailRestaurantResponse
import com.dicoding.core.data.source.remote.response.ListRestaurantResponse
import com.dicoding.core.data.source.remote.response.Restaurant
import com.dicoding.core.data.source.remote.response.RestaurantsItem
import com.dicoding.core.data.source.remote.response.SearchRestaurantResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody

class RemoteDataSource (private val apiService: ApiService){
    suspend fun getAllRestaurant(): List<RestaurantsItem> = apiService.getAllRestaurant().restaurants
    suspend fun getDetailRestaurant(id: String):Restaurant = apiService.getDetailRestaurant(id).restaurant
    suspend fun searchRestaurant(query: String):List<RestaurantsItem> = apiService.searchRestaurant(query).restaurants
    suspend fun postReview(body: PostReview):List<CustomerReviewsItem> = apiService.postReview(body).customerReviews
}