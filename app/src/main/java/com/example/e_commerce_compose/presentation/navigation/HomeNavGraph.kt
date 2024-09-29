package com.example.e_commerce_compose.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
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
import com.example.e_commerce_compose.presentation.screens.profile.orders.Orders
import com.example.e_commerce_compose.presentation.screens.profile.shopping_address.AddressesScreen
import com.example.e_commerce_compose.presentation.screens.profile.update_info.UpdateInfoScreen
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
        route = Graph.HOME,
        startDestination = Screens.Home.route,
        modifier = Modifier
            .padding(bottom =  paddingValues.calculateBottomPadding())
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
                },
                navigateToAddresses = {
                    navController.navigate(Screens.ShoppingAddress.route)
                }
            )
        }
        checkoutNavGraph(navController = navController)

        composable(
            route = Screens.ProductDetails.route,
            arguments = listOf(navArgument("productId"){
                type = NavType.StringType
                nullable = true
            }),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = androidx.compose.animation.core.tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = androidx.compose.animation.core.tween(400)
                )
            }
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
        composable(
            route = Screens.BrowseProducts.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = androidx.compose.animation.core.tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = androidx.compose.animation.core.tween(400)
                )
            }
        ){
            BrowseProductsScreen(
                navController = navController
            )
        }

        composable(route = Screens.MyOrders.route){
            Orders()
        }
        composable(route = Screens.ShoppingAddress.route){
            AddressesScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screens.EditProfile.route){
            UpdateInfoScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}