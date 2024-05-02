package com.dicoding.core.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val restaurantUseCase: RestaurantUseCase
):ViewModel() {

    private val _username = MutableStateFlow<String?>(null)
    val username:StateFlow<String?> = _username

    init {
        getUsername()
    }

    fun saveUsername(token: String) {
        viewModelScope.launch {
            restaurantUseCase.saveUsername(token)
        }
    }

    private fun getUsername() {
        viewModelScope.launch {
            restaurantUseCase.getUsername().collect{
                _username.value = it
            }
        }
    }
}