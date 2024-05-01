package com.dicoding.restaurantfinder.di

import com.dicoding.core.utils.FavoriteViewModel
import com.dicoding.core.domain.usecase.RestaurantInteractor
import com.dicoding.core.domain.usecase.RestaurantUseCase
import com.dicoding.core.utils.SettingsViewModel
import com.dicoding.restaurantfinder.presentation.ui.screen.detail.DetailViewModel
import com.dicoding.restaurantfinder.presentation.ui.screen.home.HomeViewModel
import com.dicoding.restaurantfinder.presentation.ui.screen.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<RestaurantUseCase> { RestaurantInteractor(get()) }
}

val viewModel = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}