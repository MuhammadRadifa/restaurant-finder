package com.dicoding.restaurantfinder.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dicoding.core.data.common.Screen
import com.dicoding.core.utils.SettingsViewModel
import com.dicoding.restaurantfinder.R
import com.dicoding.restaurantfinder.data.common.itemNavigation
import com.dicoding.restaurantfinder.presentation.ui.screen.detail.DetailScreen
import com.dicoding.restaurantfinder.presentation.ui.screen.home.HomeScreen
import com.dicoding.restaurantfinder.presentation.ui.screen.search.SearchScreen
import com.dicoding.restaurantfinder.presentation.ui.screen.welcome.WelcomeScreen
import com.dicoding.restaurantfinder.utils.dfFavorite
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        modifier = Modifier
            .height(64.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.red)
        ),
        title = {
            Text(
                text = "Restaurant Finder",
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp),
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp),
                painter = painterResource(id = R.drawable.grommet_icons_restaurant),
                contentDescription = "Icon",
                tint = Color.White
            )
        }
    )
}

@Composable
fun MainApp(settingsViewModel: SettingsViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    val isNotDetailScreen = backStackEntry?.destination?.route != Screen.Detail.route
    val isNotWelcomeScreen = backStackEntry?.destination?.route != Screen.Welcome.route
    Scaffold(
        topBar = {
            if (isNotWelcomeScreen) {
                TopBar()
            }
        },
        bottomBar = {
            if (isNotWelcomeScreen && isNotDetailScreen) {
                BottomBar(navController = navController, navBackStackEntry = backStackEntry)
            }
        },
        floatingActionButton = {
            if (isNotWelcomeScreen && isNotDetailScreen) {
                FloatingButton(navController = navController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = colorResource(id = R.color.gray)
    ) { innerPadding ->
        settingsViewModel.username.collectAsState().value.let {
            NavHost(
                navController = navController,
                startDestination = if (it == null) Screen.Welcome.route else Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(navigationToDetail = { id ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("id", id)
                        navController.navigate(Screen.Detail.route)
                    })
                }
                composable(Screen.Search.route) {
                    SearchScreen(
                        navigationToDetail = { id ->
                            navController.currentBackStackEntry?.savedStateHandle?.set("id", id)
                            navController.navigate(Screen.Detail.route)
                        }
                    )
                }
                composable(Screen.Detail.route) {
                    val id =
                        navController.previousBackStackEntry?.savedStateHandle?.get<String>("id")
                            ?: ""
                    DetailScreen(id = id)
                }
                composable(Screen.Welcome.route) {
                    WelcomeScreen(navController = navController)
                }

                //dynamic feature
                dfFavorite(paddingValues = innerPadding, navigationToDetail = { id ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("id", id)
                    navController.navigate(Screen.Detail.route)
                })
            }
        }
    }
}


@Composable
fun BottomBar(
    navController: NavHostController, navBackStackEntry: NavBackStackEntry?
) {
    NavigationBar(
        containerColor = colorResource(id = R.color.white)
    ) {
        itemNavigation.forEach { items ->
            val isSelected =
                items.title == (navBackStackEntry?.destination?.route ?: Screen.Home.route)
            NavigationBarItem(
                modifier = Modifier.padding(vertical = 20.dp),
                selected = isSelected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.red),
                    unselectedIconColor = colorResource(id = R.color.gray_dark),
                    indicatorColor = Color.White
                ),
                onClick = {
                    navController.navigate(items.title) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    if (items.title != "WhiteSpace") {
                        Icon(
                            painter = painterResource(id = items.icon),
                            contentDescription = items.title,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun FloatingButton(
    navController: NavController
) {
    FloatingActionButton(
        modifier = Modifier
            .offset(y = 60.dp)
            .size(80.dp)
            .border(
                width = 4.dp,
                color = colorResource(id = R.color.old_red),
                shape = CircleShape
            ),
        shape = CircleShape,
        onClick = {
            navController.navigate(Screen.Home.route)
        },
        containerColor = colorResource(id = R.color.red),
        contentColor = colorResource(id = R.color.white)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.grommet_icons_restaurant),
            contentDescription = "Home",
            modifier = Modifier.size(40.dp),
            tint = Color.White
        )
    }
}
