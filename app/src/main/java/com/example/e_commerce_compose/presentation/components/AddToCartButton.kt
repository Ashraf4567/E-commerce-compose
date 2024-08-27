package com.example.e_commerce_compose.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.poppins

@Composable
fun AddToCartButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isInCart: Boolean = false
) {
    Row(
        modifier = modifier
            .width(250.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(PrimaryBlue)
            .padding(horizontal = 15.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isInCart) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "cart icon",
                tint = Color.White,
                modifier = Modifier
                    .size(25.dp)
            )
        }else {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "cart icon",
                tint = Color.White,
                modifier = Modifier.size(25.dp)
            )
        }
        Text(
            text = "Add to cart",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.weight(1f),
            fontFamily = poppins,
            fontSize = 20.sp
        )
        AnimatedVisibility(visible = isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Preview
@Composable
private fun AddToCartButtonPreview() {
    EcommerceComposeTheme {
        AddToCartButton()
    }
}