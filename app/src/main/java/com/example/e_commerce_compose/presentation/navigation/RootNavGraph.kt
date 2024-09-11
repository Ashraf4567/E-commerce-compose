package com.example.e_commerce_compose.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e_commerce_compose.presentation.screens.home.Home

@Composable
fun RootNavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Graph.AUTHENTICATION,
        route = Graph.ROOT,
        modifier = Modifier
    ) {
        authNavGraph(navController = navController)
        //this should br composable of home screen which will contain another nav controller with different nav graph
        composable(route = Graph.Home){
            Home()
        }


        composable(route = Screens.MyOrders.route){}
        composable(route = Screens.ShoppingAddress.route){}

    }

}





