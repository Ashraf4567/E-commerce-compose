package com.example.e_commerce_compose.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.e_commerce_compose.presentation.screens.browse_products.BrowseProductsScreen
import com.example.e_commerce_compose.presentation.screens.browse_products.BrowseProductsViewModel
import com.example.e_commerce_compose.presentation.screens.cart.CartScreen
import com.example.e_commerce_compose.presentation.screens.cart.CartViewModel
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesScreen
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesViewModel
import com.example.e_commerce_compose.presentation.screens.checkout.CheckoutScreen
import com.example.e_commerce_compose.presentation.screens.checkout.CheckoutViewModel
import com.example.e_commerce_compose.presentation.screens.home.HomeScreen
import com.example.e_commerce_compose.presentation.screens.home.HomeViewModel
import com.example.e_commerce_compose.presentation.screens.login.SignInScreen
import com.example.e_commerce_compose.presentation.screens.login.SignInViewModel
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsScreen
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsViewModel
import com.example.e_commerce_compose.presentation.screens.profile.ProfileScreen
import com.example.e_commerce_compose.presentation.screens.wishlist.Wishlist
import com.example.e_commerce_compose.presentation.screens.wishlist.WishlistViewModel
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
            HomeScreen(
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
                wishlistState = state.value
            )
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
            ProductDetailsScreen(
                viewModel = productDetailsViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onEvent = productDetailsViewModel::onEvent
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

        composable(route = Screens.Cart.route){
            val cartViewModel: CartViewModel = koinViewModel()
            CartScreen(
                cartViewModel = cartViewModel,
                onBackClicked = {
                    navController.popBackStack()
                },
                onCheckoutClicked = {
                    navController.navigate(Screens.Checkout.route)
                }
            )
        }

        composable(route = Screens.Checkout.route,
            // i need no make the enter transition for this screen from bottom to top
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(400)
                )
            }
            ){
            val checkoutViewModel: CheckoutViewModel = koinViewModel()
            CheckoutScreen(viewModel = checkoutViewModel)
        }

        composable(route = Screens.BrowseProducts.route){
            BrowseProductsScreen(
                navController = navController
            )
        }

    }

}