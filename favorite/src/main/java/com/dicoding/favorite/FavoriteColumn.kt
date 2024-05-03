package com.dicoding.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dicoding.core.BuildConfig
import com.dicoding.core.domain.model.FavoriteModel
import com.dicoding.core.utils.FavoriteViewModel
import com.dicoding.restaurantfinder.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteList(favoriteViewModel: FavoriteViewModel = koinViewModel(),navigationToDetail: (String) -> Unit) {
    favoriteViewModel.getAllFavorites().collectAsState(initial = emptyList()).value.let {
        if (it.isNotEmpty()) {
            LazyColumn {
                items(it) { item ->
                    ItemCard(item,navigationToDetail)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(restaurantsItem: FavoriteModel,navigationToDetail: (String) -> Unit) {

    val image = BuildConfig.API_URL + "images/medium/" + restaurantsItem.pictureId

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(12.dp),
        onClick = { navigationToDetail(restaurantsItem.id) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                painter = rememberAsyncImagePainter(image),
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = restaurantsItem.name,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(${restaurantsItem.rating})",
                        color = Color.Black,
                        fontSize = 14.sp,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_location_on_24),
                        contentDescription = "location",
                        tint = colorResource(id = R.color.red)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = restaurantsItem.city,
                        color = Color.Black,
                        fontSize = 14.sp,
                    )
                }
            }
            Text(
                text = restaurantsItem.description,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp),
                maxLines = 3,
                textAlign = TextAlign.Justify,
            )
        }
    }
}