package com.example.e_commerce_compose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

@Composable
fun CountBar(
    modifier: Modifier,
    onCountChange: (Int) -> Unit = {},
    initialCount: Int = 1,
    isPlusButtonEnabled: Boolean = true,
    isMinusButtonEnabled: Boolean = true,
) {

    Row(
        modifier = modifier
            .width(122.dp)
            .height(42.dp)
            .clip(RoundedCornerShape(80.dp))
            .background(PrimaryBlue)
            .padding(horizontal = 16.dp , vertical = 4.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
            contentDescription = "add",
            modifier = Modifier
                .clickable(
                    enabled = isPlusButtonEnabled
                ) {
                    onCountChange(initialCount+1)
                }
        )
        Text(text = initialCount.toString() , color = Color.White , fontSize = 18.sp)
        Image(
            painter = painterResource(id = R.drawable.baseline_remove_circle_outline_24),
            contentDescription = "remove",
            modifier = Modifier
                .clickable(
                    enabled = isMinusButtonEnabled
                ) {
                    if (initialCount > 0) {
                        onCountChange(initialCount - 1)
                    }
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CountBarPrev() {
    EcommerceComposeTheme {
        CountBar(Modifier)
    }
}