package com.example.e_commerce_compose.presentation.screens.browse_products

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.domain.model.Brand
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.poppins

@Composable
fun BrandItem(
    modifier: Modifier = Modifier,
    brand: Brand,
    isSelected: Boolean = false,
    onClick: (brandId: String) -> Unit = {}
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) PrimaryBlue.copy(alpha = 0.05f) else Color.White)
                .border(
                    if (isSelected) BorderStroke(2.dp, PrimaryBlue) else BorderStroke(2.dp, Color.DarkGray.copy(alpha = 0.2f)),
                    RoundedCornerShape(10.dp)
                )
                .clickable {
                    onClick(brand.id?:"")
                },
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                model = brand.image,
                contentDescription = brand.name + " logo",
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.placeholder),
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp)
            )

        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = brand.name ?: "",
            fontFamily = poppins,
            style = MaterialTheme.typography.labelMedium
        )

    }
}

@Preview(showBackground = true)
@Composable
fun BrandItemPrev(modifier: Modifier = Modifier) {
    EcommerceComposeTheme {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BrandItem(
                brand = Brand(
                    image = "https://ecommerce.routemisr.com/Route-Academy-brands/1678286824747.png",
                    name = "Canon",
                    id = "64089fe824b25627a25315d1",
                    slug = "canon"
                ), isSelected = true
            )
        }

    }
}