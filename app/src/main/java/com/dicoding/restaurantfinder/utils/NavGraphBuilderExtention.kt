package com.dicoding.restaurantfinder.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dicoding.core.data.common.Screen

fun NavGraphBuilder.dfFavorite(paddingValues: PaddingValues,navigationToDetail: (String) -> Unit) {
    composable(route = Screen.Favorite.route) {
        DFFavorite(paddingValues = paddingValues,navigationToDetail = navigationToDetail)
    }
}