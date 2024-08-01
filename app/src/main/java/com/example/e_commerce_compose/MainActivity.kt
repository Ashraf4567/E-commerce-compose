package com.example.e_commerce_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_compose.presentation.navigation.SetupNavGraph
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            EcommerceComposeTheme {
                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
//
//                val currentBackStackEntry = navController.currentBackStackEntry
//                val currentDestination = currentBackStackEntry?.destination

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    selectedItemIndex = bottomNavigationItems.indexOfFirst {
                        it.route == destination.route
                    }.coerceAtLeast(0)

                }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = PrimaryBlue,
                            modifier = Modifier
                                .height(60.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 15.dp,
                                        topEnd = 15.dp
                                    )
                                ),
                        ) {
                            bottomNavigationItems.forEachIndexed { index, item ->
                                NavigationBarItem(

                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = Color.White,
                                    ),
                                    selected =selectedItemIndex == index ,
                                    onClick = {
                                        selectedItemIndex = index
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId){
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = item.iconRes),
                                            contentDescription = "",
                                            tint = if (selectedItemIndex == index) PrimaryBlue else Color.White,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    },
                                    modifier = Modifier.clip(CircleShape)

                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    SetupNavGraph(
                        paddingValues = innerPadding,
                        navController = navController
                    )
                }
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

