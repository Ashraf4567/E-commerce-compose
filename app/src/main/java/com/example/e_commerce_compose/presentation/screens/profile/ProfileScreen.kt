package com.example.e_commerce_compose.presentation.screens.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateToEditProfile: () -> Unit = {},
    navigateToMyOrders: () -> Unit = {},
    navigateToAddresses: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Profile")
                },
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 8.dp , vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            profileImage(
                onEditClick = navigateToEditProfile
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)            ) {
                items(profileListItems.size) {
                    profileListItem(
                        modifier = Modifier.height(70.dp),
                        title = profileListItems[it].title,
                        iconRes = profileListItems[it].iconRes,
                        onClick = {title->
                            when(title){
                                "My Orders" -> navigateToMyOrders()
                                "My Information" -> navigateToEditProfile()
                                "Shipping Addresses" -> navigateToAddresses()
                                "Payment Methods" -> {}
                                "Settings" -> {}
                                "Logout" -> {}
                            }
                        }
                    )
                }
            }
        }
    }
}

val profileListItems = listOf(
    ProfileListItem(
        title = "My Orders",
        iconRes = R.drawable.baseline_shopping_bag_24
    ),
    ProfileListItem(
        title = "My Information",
        iconRes = R.drawable.baseline_info_outline_24,
    ),
    ProfileListItem(
        title = "Shipping Addresses",
        iconRes = R.drawable.baseline_location_pin_24,
    ),
    ProfileListItem(
        title = "Payment Methods",
        iconRes = R.drawable.baseline_credit_card_24,
    ),
    ProfileListItem(
        title = "Settings",
        iconRes = R.drawable.baseline_settings_24,
    ),
    ProfileListItem(
        title = "Logout",
        iconRes = R.drawable.baseline_power_settings_new_24,
    )
)

data class ProfileListItem(
    val title: String,
    val iconRes: Int
)