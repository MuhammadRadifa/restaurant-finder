package com.dicoding.restaurantfinder.data.common

import com.dicoding.restaurantfinder.R

data class NavigationItem(
    val title:String,
    val icon:Int,
)

val itemNavigation = listOf(
    NavigationItem(
        title = "Favorite",
        icon = R.drawable.baseline_favorite_24
    ),
    NavigationItem(
        title = "WhiteSpace",
        icon = R.drawable.grommet_icons_restaurant
    ),
    NavigationItem(
        title = "Search",
        icon = R.drawable.baseline_manage_search_24
    ),
)