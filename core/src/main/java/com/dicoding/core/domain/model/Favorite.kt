package com.dicoding.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteModel(
    val id: String,
    val pictureId: String,
    val city: String,
    val name: String,
    val rating: String,
    val description: String,
):Parcelable