package com.dicoding.restaurantfinder.presentation.ui.screen.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dicoding.core.BuildConfig
import com.dicoding.core.domain.model.CustomerReviewsItemModel
import com.dicoding.core.domain.model.DetailRestaurantModel
import com.dicoding.core.domain.model.FavoriteModel
import com.dicoding.core.utils.FavoriteViewModel
import com.dicoding.core.utils.SettingsViewModel
import com.dicoding.restaurantfinder.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailScreen(
    id: String,
    detailViewModel: DetailViewModel = koinViewModel(),
    settingPreferences: SettingsViewModel = koinViewModel(),
    favoriteViewModel: FavoriteViewModel = koinViewModel()
) {

    var textUrl by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Log.i("DetailScreen", "DetailScreen: $id")
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .verticalScroll(state = rememberScrollState())
    ) {

        detailViewModel.detailRestaurant(id)

        detailViewModel.restaurant.collectAsState().value.let { restaurant ->
            when {
                restaurant.loading -> {
                    Text(text = "Loading")
                }

                restaurant.error != null -> {
                    Text(text = restaurant.error!!)
                }

                restaurant.data != null -> {
                    restaurant.data?.let { item ->
                        val image = BuildConfig.API_URL + "images/medium/" + item.pictureId
                        Spacer(modifier = Modifier.height(12.dp))
                        Image(
                            painter = rememberAsyncImagePainter(image),
                            contentDescription = "restaurant image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .clip(shape = RoundedCornerShape(8.dp)),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        CardDescription(data = item)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Review",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            settingPreferences.username.collectAsState().value.let { username ->
                                MySendInput(
                                    text = textUrl,
                                    onTextChange = {
                                        textUrl = it
                                    },
                                    placeHolder = "Send a message",
                                    keyboardController = keyboardController,
                                    onClick = {
                                        detailViewModel.postReviewRestaurant(
                                            id,
                                            username!!,
                                            textUrl
                                        )
                                        textUrl = ""
                                    }
                                )
                            }

                            favoriteViewModel.getFavoriteByName(id).collectAsState(
                                initial = null
                            ).value?.let { fav ->
                                val isFavorite = fav.id != ""
                                val favoriteData = FavoriteModel(
                                    id = item.id,
                                    pictureId = item.pictureId,
                                    city = item.city,
                                    name = item.name,
                                    rating = item.rating,
                                    description = item.description
                                )
                                IconButton(
                                    onClick = {
                                        if (isFavorite) {
                                            favoriteViewModel.deleteFavorite(favoriteData)
                                        } else {
                                            favoriteViewModel.addFavorite(favoriteData)
                                        }
                                    },
                                    modifier = Modifier
                                        .size(50.dp)
                                        .background(
                                            color = colorResource(id = R.color.red),
                                            shape = CircleShape
                                        )
                                        .border(
                                            width = 4.dp,
                                            color = colorResource(id = R.color.old_red),
                                            shape = CircleShape
                                        ),
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_favorite_24),
                                        contentDescription = "Favorite",
                                        modifier = Modifier.size(30.dp),
                                        tint = colorResource(id = if (isFavorite) R.color.old_red else R.color.white)
                                    )
                                }
                            }


                        }

                        detailViewModel.reviewState.collectAsState().value.data?.let { itemReview ->
                            LazyColumn(Modifier.height(165.dp)) {
                                items(itemReview.size) { index ->
                                    CardReviewItem(itemReview = itemReview[itemReview.size - index - 1])
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun CardDescription(data: DetailRestaurantModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Column(Modifier.padding(bottom = 12.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = data.name,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = data.rating,
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
                        text = data.city,
                        color = Color.Black,
                        fontSize = 14.sp,
                    )
                }
            }
            Text(
                text = data.description,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp),
                textAlign = TextAlign.Justify,
                maxLines = 4
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MySendInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    placeHolder: String,
    keyboardController: SoftwareKeyboardController?,
    onClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.82f),
        value = text,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        onValueChange = {
            onTextChange(it)
        },
        placeholder = {
            Text(
                text = placeHolder,
                fontWeight = FontWeight.Normal
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.Black,
            focusedBorderColor = colorResource(id = R.color.red),
            unfocusedBorderColor = colorResource(id = R.color.gray_dark),
        ),
        shape = RoundedCornerShape(8.dp),
        trailingIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send"
                )
            }
        },
        maxLines = 1,
    )
}


@Composable
fun CardReviewItem(
    itemReview: CustomerReviewsItemModel
) {
    Card(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_people_alt_24),
                contentDescription = "Icon",
                tint = colorResource(id = R.color.old_red),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = itemReview.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = itemReview.review,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.black)
                )
            }
        }
    }

}

