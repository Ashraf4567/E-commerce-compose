package com.example.e_commerce_compose.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesScreen
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesViewModel
import com.example.e_commerce_compose.presentation.screens.home.HomeScreen
import com.example.e_commerce_compose.presentation.screens.profile.ProfileScreen
import com.example.e_commerce_compose.presentation.screens.wishlist.Wishlist

@Composable
fun SetupNavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
        modifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding()+5.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        composable(route = Screens.Home.route){
            HomeScreen()
        }
        composable(route = Screens.Categories.route){

            val categoriesViewModel: CategoriesViewModel = hiltViewModel()
            val state = categoriesViewModel.state.collectAsState()

            CategoriesScreen(
                state = state.value,
                onEvent = categoriesViewModel::onEvent
            )
        }
        composable(route = Screens.WishList.route){
            Wishlist()
        }
        composable(route = Screens.Profile.route){
            ProfileScreen()
        }

    }

}