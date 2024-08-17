package com.example.e_commerce_compose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme

@Composable
fun ColorBalletSelector(modifier: Modifier = Modifier) {
    val colors = listOf(
        Color.Black,
        Color.LightGray,
        Color.Red,
        Color.Blue,
        Color.Green,
    )

    Row(
        modifier = modifier
            .width(160.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        colors.forEachIndexed {index ,color->
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(
                        RoundedCornerShape(50.dp)
                    )
                    .background(color),
                contentAlignment = Alignment.Center
            ){
                if (index == 0){
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "check",
                        tint = Color.White,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorBalletSelectorPreview() {
    EcommerceComposeTheme {
        ColorBalletSelector()
    }
}