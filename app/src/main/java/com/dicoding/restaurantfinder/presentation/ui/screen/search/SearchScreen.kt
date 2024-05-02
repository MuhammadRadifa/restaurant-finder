package com.dicoding.restaurantfinder.presentation.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.dicoding.restaurantfinder.R
import com.dicoding.restaurantfinder.presentation.ui.screen.home.ItemCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navigationToDetail:(String)->Unit,searchViewModel: SearchViewModel = koinViewModel()) {
    var textUrl by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        Modifier
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        MySearchBar(
            text = textUrl,
            onTextChange = {
                textUrl = it
            },
            placeHolder = "Search Restaurant",
            keyboardController = keyboardController,
            onClick = {
                searchViewModel.searchRestaurant(textUrl)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        searchViewModel.search.collectAsState().value.let {
            search ->
            when {
                search.loading -> {
                    Text(text = "no result")
                }
                search.error != null -> {
                    Text(text = search.error!!)
                }
                search.data != null -> {
                    LazyColumn {
                        search.data?.let {
                                item ->
                            items(item.size) { index ->
                                ItemCard(restaurantsItem = item[index], navigationToDetail = navigationToDetail)
                            }
                        }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MySearchBar(
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
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        value = text,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onClick()
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
        leadingIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = modifier.size(22.dp)
                )
            }
        },
    )
}