package com.dicoding.core.data.source.local

import com.dicoding.core.data.source.local.preferences.SettingPreferences
import com.dicoding.core.data.source.local.room.FavoriteDao
import com.dicoding.core.data.source.local.room.FavoriteEntity

class LocalDataSource(
    private val favoriteDao: FavoriteDao,
    private val settingsPreferences: SettingPreferences
) {

    suspend fun addFavorite(favorite: FavoriteEntity) {
        favoriteDao.addFavorite(favorite)
    }

    fun getAllFavorites() = favoriteDao.getAllFavorites()

    fun getFavoriteById(id: String) = favoriteDao.getFavoriteById(id)

    suspend fun deleteFavoriteByName(favorite: FavoriteEntity) {
        favoriteDao.deleteFavoriteByName(favorite)
    }

    fun getUsername() = settingsPreferences.getUsername()

    suspend fun saveUsername(token: String) {
        settingsPreferences.saveUsername(token)
    }

}