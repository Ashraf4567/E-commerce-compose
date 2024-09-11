package com.example.e_commerce_compose.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.e_commerce_compose.bottomNavigationItems
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onSelectedItem: (Int) -> Unit
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
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
                selected = currentDestination?.route == item.route ,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                    onSelectedItem(index)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = "",
                        tint = if (currentDestination?.route == item.route) PrimaryBlue else Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                },
                modifier = Modifier.clip(CircleShape)

            )
        }
    }
}