package com.example.e_commerce_compose.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_compose.bottomNavigationItems
import com.example.e_commerce_compose.presentation.components.BottomNavBar
import com.example.e_commerce_compose.presentation.navigation.HomeNavGraph
import com.example.e_commerce_compose.presentation.navigation.Screens

@Composable
fun Home(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val screens = listOf(
        Screens.Home.route,
        Screens.Categories.route,
        Screens.WishList.route,
        Screens.Profile.route
    )

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination


    val bottomBarDistinctions = screens.any { it == currentDestination?.route }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        bottomBar = {

            if (bottomBarDistinctions) {
                BottomNavBar(
                    navController = navController,
                    onSelectedItem = {
                        selectedItemIndex = it
                    }
                )
            }


        }
    ){paddingValues ->
        HomeNavGraph(paddingValues , navController)
    }
}