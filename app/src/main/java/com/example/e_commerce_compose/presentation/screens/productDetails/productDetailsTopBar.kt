package com.example.e_commerce_compose.presentation.screens.productDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.PrimaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsTopBar(
    onBackClicked: () -> Unit,
) {

    CenterAlignedTopAppBar(
        title = { Text(text = "Product Details" , color = PrimaryText)},
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

             IconButton(onClick = { /*TODO*/ }) {
                 Icon(
                     imageVector = Icons.Outlined.ShoppingCart,
                     contentDescription = "Cart",
                     tint = PrimaryBlue
                 )
             }

         },
        modifier = Modifier
            .background(Color.White)
    )
}
