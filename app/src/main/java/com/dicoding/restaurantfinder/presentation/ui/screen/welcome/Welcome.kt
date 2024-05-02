package com.dicoding.restaurantfinder.presentation.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import com.dicoding.core.data.common.Screen
import com.dicoding.core.utils.SettingsViewModel
import com.dicoding.restaurantfinder.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = koinViewModel()
) {

    var textUrl by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.red)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_red),
            contentDescription = "Welcome"
        )
        Column(
            Modifier
                .padding(20.dp)
                .height(300.dp)
                .clip(shape = RoundedCornerShape(10))
                .background(color = Color.White)
        ) {
            Column(
                Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.text_gray),
                    textAlign = TextAlign.Center,
                    text = "Please Fill Your Username,\n" +
                            "take part in find of your restaurant"
                )
                Spacer(modifier = Modifier.height(16.dp))
                MyUsernameTextField(
                    text = textUrl,
                    onTextChange = {
                        textUrl = it
                    },
                    placeHolder = "Fill your username",
                    keyboardController = keyboardController
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (textUrl.isNotEmpty()) {
                            settingsViewModel.saveUsername(textUrl)
                            navController.navigate(Screen.Home.route){
                                popUpTo(Screen.Welcome.route){
                                    inclusive = true
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.red),
                        contentColor = colorResource(id = R.color.white)
                    )
                ) {
                    if (textUrl.isNotEmpty()) {
                        Text(text = "Let's Find", fontSize = 18.sp)
                    } else {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Lock",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyUsernameTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    placeHolder: String,
    keyboardController: SoftwareKeyboardController?
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
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
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.text_gray)
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
        maxLines = 1
    )
}