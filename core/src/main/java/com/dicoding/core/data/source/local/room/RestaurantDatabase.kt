package com.dicoding.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

}