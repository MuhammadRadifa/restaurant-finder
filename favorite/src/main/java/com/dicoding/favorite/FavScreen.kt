package com.dicoding.favorite

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
import com.dicoding.core.utils.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

/*
 * this class is used by reflection
 */


@Suppress("unused")
@Composable
fun FavoriteScreen(PaddingValues: PaddingValues,navigationToDetail: (String) -> Unit){
    Column(modifier = Modifier.padding(10.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your Favorite Restaurant", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        FavoriteList(navigationToDetail = navigationToDetail)
    }
}