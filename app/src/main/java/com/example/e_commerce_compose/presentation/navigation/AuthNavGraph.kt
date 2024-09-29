package com.example.e_commerce_compose.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.e_commerce_compose.presentation.screens.login.SignInScreen
import com.example.e_commerce_compose.presentation.screens.login.SignInViewModel
import com.example.e_commerce_compose.presentation.screens.signup.SignupScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController){
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Screens.SignIn.route
    ){
        composable(route = Screens.SignIn.route){
            val signInViewModel: SignInViewModel = koinViewModel()
            SignInScreen(
                viewModel = signInViewModel,
                navController = navController
            )
        }
        composable(route = Screens.SignUp.route){
            SignupScreen(navController = navController)
        }
    }
}