package com.dicoding.core.data.source.remote

data class ResponseState<T>(
    val loading: Boolean = true,
    val data: T? = null,
    val error: String? = null
)