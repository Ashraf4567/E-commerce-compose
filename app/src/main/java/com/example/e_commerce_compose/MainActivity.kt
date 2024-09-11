package com.example.e_commerce_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_compose.presentation.navigation.RootNavGraph
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            EcommerceComposeTheme {
                RootNavGraph(navController = navController , paddingValues = PaddingValues(bottom = 20.dp))
            }
        }
    }
}

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "Home",
        iconRes = R.drawable.ic_home,
        route = "home"
    ),
    BottomNavigationItem(
        title = "Categories",
        R.drawable.ic_category,
        route = "categories"
    ),
    //wishList
    BottomNavigationItem(
        title = "WishList",
        iconRes = R.drawable.ic_wishlist_outlined,
        route = "wishList"
    ),
    //profile
    BottomNavigationItem(
        title = "Profile",
        iconRes = R.drawable.ic_profile,
        route = "profile"
    )
)

data class BottomNavigationItem(
    val title: String,
    val iconRes: Int,
    val route: String,
)

