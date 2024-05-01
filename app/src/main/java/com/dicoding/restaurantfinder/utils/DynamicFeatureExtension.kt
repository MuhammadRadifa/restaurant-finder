package com.dicoding.restaurantfinder.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
fun DFFavorite(paddingValues: PaddingValues,navigationToDetail: (String) -> Unit) {
    DynamicFeatureUtils().dfFavoriteScreen(paddingValues = paddingValues,navigationToDetail = navigationToDetail)
}