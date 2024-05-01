package com.dicoding.core.data.source.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "pictureId")
    val pictureId: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "rating")
    val rating: String,
    @ColumnInfo(name = "description")
    val description: String,
)