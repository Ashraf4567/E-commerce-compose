package com.example.e_commerce_compose.presentation.screens.productDetails

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.PrimaryText
import com.example.e_commerce_compose.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String = "Product Details",
    onBackClicked: () -> Unit,
    showCartIcon: Boolean = true,
    onFilterClicked: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
) {

    CenterAlignedTopAppBar(
        title = { Text(
            text = title,
            color = PrimaryText,
            fontFamily = poppins,
        )},
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                )
            }
        },
         actions = {
             IconButton(onClick = { /*TODO*/ }) {
                 Icon(
                     imageVector = Icons.Outlined.Search,
                     contentDescription = "Cart",
                     tint = PrimaryBlue
                 )
             }
             if (showCartIcon){
                 IconButton(onClick = { /*TODO*/ }) {
                     Icon(
                         imageVector = Icons.Outlined.ShoppingCart,
                         contentDescription = "Cart",
                         tint = PrimaryBlue
                     )
                 }
             }
             if (!showCartIcon) {
                 IconButton(onClick = onFilterClicked) {
                     Icon(
                         painter = painterResource(R.drawable.baseline_filter_list_24),
                         contentDescription = "filter"
                     )
                 }
             }


         },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = PrimaryText,
            navigationIconContentColor = PrimaryBlue,
            actionIconContentColor = PrimaryBlue
        )
    )
}
