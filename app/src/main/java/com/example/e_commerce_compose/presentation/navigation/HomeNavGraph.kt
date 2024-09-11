package com.example.e_commerce_compose.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.e_commerce_compose.presentation.screens.browse_products.BrowseProductsScreen
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesScreen
import com.example.e_commerce_compose.presentation.screens.home.HomeScreenContent
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsScreen
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsViewModel
import com.example.e_commerce_compose.presentation.screens.profile.ProfileScreen
import com.example.e_commerce_compose.presentation.screens.wishlist.Wishlist
import com.example.e_commerce_compose.presentation.screens.wishlist.WishlistViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeNavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        route = Graph.Home,
        startDestination = Screens.Home.route,
        modifier = Modifier
            .padding(paddingValues)
    ){
        composable(route = Screens.Home.route){
            HomeScreenContent(
                navController = navController
            )
        }
        composable(route = Screens.Categories.route){

            CategoriesScreen(
                navigateToProducts = {
                    navController.navigate(Screens.BrowseProducts.createRoute(it))
                }
            )
        }
        composable(route = Screens.WishList.route){
            val wishlistViewModel: WishlistViewModel = koinViewModel()
            val state = wishlistViewModel.state.collectAsState()
            Wishlist(
                wishlistState = state.value,
                onEvent = wishlistViewModel::onEvent
            )
        }

        composable(route = Screens.Profile.route){
            ProfileScreen(
                navigateToEditProfile = {
                    navController.navigate(Screens.EditProfile.route)
                },
                navigateToMyOrders = {
                    navController.navigate(Screens.MyOrders.route)
                }
            )
        }
        checkoutNavGraph(navController = navController)

        composable(
            route = Screens.ProductDetails.route,
            arguments = listOf(navArgument("productId"){
                type = NavType.StringType
                nullable = true
            })
        ){
            val productDetailsViewModel: ProductDetailsViewModel = koinViewModel()
            ProductDetailsScreen(
                viewModel = productDetailsViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onEvent = productDetailsViewModel::onEvent
            )

        }
        composable(route = Screens.BrowseProducts.route){
            BrowseProductsScreen(
                navController = navController
            )
        }

        composable(route = Screens.MyOrders.route){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Text(text = "My Orders")
            }
        }
        composable(route = Screens.ShoppingAddress.route){}
        composable(route = Screens.EditProfile.route){}
    }
}