package com.dicoding.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getFavoriteById(id: String): Flow<FavoriteEntity>

    @Delete
    suspend fun deleteFavoriteByName(favorite: FavoriteEntity)

}