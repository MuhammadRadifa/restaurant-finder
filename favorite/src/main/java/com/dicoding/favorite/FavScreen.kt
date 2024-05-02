package com.dicoding.favorite

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
 * this class is used by reflection
 */


@Suppress("unused")
@Composable
fun FavoriteScreen(paddingValues: PaddingValues, navigationToDetail: (String) -> Unit){
    Log.i("FavoriteScreen", paddingValues.toString())
    Column(modifier = Modifier.padding(10.dp)) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Your Favorite Restaurant", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        FavoriteList(navigationToDetail = navigationToDetail)
    }
}