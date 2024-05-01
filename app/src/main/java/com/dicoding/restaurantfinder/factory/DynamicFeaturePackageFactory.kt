@file:Suppress("SpellCheckingInspection")

package com.dicoding.restaurantfinder.factory

object DynamicFeaturePackageFactory {

    private const val PACKAGE = "com.dicoding."

    object DFFavorite{
        const val DF_FAVORITE = PACKAGE.plus("favorite.FavScreenKt")
        const val COMPOSE_METHOD_NAME = "FavoriteScreen"
    }
}