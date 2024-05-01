package com.dicoding.core.data.source.remote.network

import com.dicoding.core.BuildConfig
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val hostname = "restaurant-api.dicoding.dev"
            val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/uxXaY3FjavKrehG5aNOjZm15Ekq5rh1b/wmDObOXw58=")
                .build()
            val loggingInterceptor = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .certificatePinner(certificatePinner)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}