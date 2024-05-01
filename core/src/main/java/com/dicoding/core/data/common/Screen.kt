package com.dicoding.core.data.common

sealed class Screen(val route: String) {

    data object Home : Screen("Home")
    data object Favorite : Screen("Favorite")
    data object Search : Screen("Search")
    data object Detail : Screen("Detail")
    data object Welcome : Screen("Welcome")
}