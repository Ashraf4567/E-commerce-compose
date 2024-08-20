package com.example.e_commerce_compose.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesScreen
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesViewModel
import com.example.e_commerce_compose.presentation.screens.home.HomeScreen
import com.example.e_commerce_compose.presentation.screens.home.HomeViewModel
import com.example.e_commerce_compose.presentation.screens.login.SignInScreen
import com.example.e_commerce_compose.presentation.screens.login.SignInViewModel
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsScreen
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsViewModel
import com.example.e_commerce_compose.presentation.screens.profile.ProfileScreen
import com.example.e_commerce_compose.presentation.screens.wishlist.Wishlist
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetupNavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SignIn.route,
        modifier = Modifier
            .padding(
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        composable(route = Screens.Home.route){
            val homeViewModel: HomeViewModel = koinViewModel()
            val state = homeViewModel.state.collectAsState()
            HomeScreen(
                state = state.value,
                onEvent = {
                    homeViewModel.onEvent(it , onProductClicked = {productId ->
                        navController.navigate(Screens.ProductDetails.createRoute(productId))
                    })
                }
            )
        }
        composable(route = Screens.Categories.route){

            val categoriesViewModel: CategoriesViewModel = koinViewModel()
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

        composable(
            route = Screens.ProductDetails.route,
            arguments = listOf(navArgument("productId"){
                type = NavType.StringType
                nullable = true
            })
        ){
            val productDetailsViewModel: ProductDetailsViewModel = koinViewModel()
            val state by productDetailsViewModel.state.collectAsState()
            ProductDetailsScreen(
                state = state,
                onBackClick = {
                    navController.popBackStack()
                }
            )

        }
        composable(route = Screens.SignIn.route){
            val signInViewModel: SignInViewModel = koinViewModel()
            SignInScreen(
                viewModel = signInViewModel,
                onNavigateToHome = {
                    navController.navigate(Screens.Home.route){
                        popUpTo(Screens.SignIn.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

    }

}