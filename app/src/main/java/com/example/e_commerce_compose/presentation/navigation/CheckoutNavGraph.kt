package com.example.e_commerce_compose.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.e_commerce_compose.presentation.screens.cart.CartScreenContent
import com.example.e_commerce_compose.presentation.screens.cart.CartViewModel
import com.example.e_commerce_compose.presentation.screens.checkout.CheckoutScreen
import com.example.e_commerce_compose.presentation.screens.checkout.CheckoutViewModel
import com.example.e_commerce_compose.presentation.screens.success_place_order.SuccessPlaceOrderScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.checkoutNavGraph(navController: NavHostController){
    navigation(
        route = Graph.CHECKOUT,
        startDestination = Screens.Cart.route
    ){
        composable(route = Screens.Cart.route){
            val cartViewModel: CartViewModel = koinViewModel()
            CartScreenContent(
                cartViewModel = cartViewModel,
                onBackClicked = {
                    navController.popBackStack()
                },
                onCheckoutClicked = {cartId->
                    navController.navigate(Screens.Checkout.createRoute(cartId))
                }
            )
        }

        composable(route = Screens.Checkout.route,
            arguments = listOf(navArgument("cartId"){
                type = NavType.StringType
                nullable = true
            }),
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
            CheckoutScreen(
                viewModel = checkoutViewModel,
                onNavigateToSuccess = { totalPrice, paymentMethod ->
                    navController.navigate(Screens.SuccessPlaceOrder.createRoute(totalPrice , paymentMethod)){
                        popUpTo(Screens.Cart.route){
                        }
                    }
                }
            )
        }



        composable(
            route = Screens.SuccessPlaceOrder.route,
            arguments = listOf(
                navArgument("totalPrice"){
                    type = NavType.StringType
                },
                navArgument("paymentMethod"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry ->
            val totalPrice = backStackEntry.arguments?.getString("totalPrice")
            val paymentMethod = backStackEntry.arguments?.getString("paymentMethod")
            SuccessPlaceOrderScreen(
                totalPrice = totalPrice?.toDouble()?:0.0,
                paymentMethod = paymentMethod,
                onNavigateToHome = {
                    navController.navigate(Graph.Home){
                        popUpTo(Screens.SuccessPlaceOrder.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}