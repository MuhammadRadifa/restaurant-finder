package com.dicoding.core.data.source.remote.network

import com.dicoding.core.data.common.PostReview
import com.dicoding.core.data.source.remote.response.AddRestaurantResponse
import com.dicoding.core.data.source.remote.response.DetailRestaurantResponse
import com.dicoding.core.data.source.remote.response.ListRestaurantResponse
import com.dicoding.core.data.source.remote.response.SearchRestaurantResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    suspend fun getAllRestaurant(): ListRestaurantResponse

    @GET("detail/{id}")
    suspend fun getDetailRestaurant(
        @Path("id") id: String
    ): DetailRestaurantResponse

    @GET("search")
    suspend fun searchRestaurant(
        @Query("q") query: String
    ): SearchRestaurantResponse

    @Headers("Content-Type: application/json")
    @POST("review")
    suspend fun postReview(
        @Body body: PostReview
    ): AddRestaurantResponse

}