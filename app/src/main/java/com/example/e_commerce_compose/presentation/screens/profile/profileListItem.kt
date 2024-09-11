package com.example.e_commerce_compose.presentation.screens.profile

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

@Composable
fun profileListItem(
    modifier: Modifier = Modifier,
    title: String,
    iconRes: Int,
    onClick: (pageName: String) -> Unit = {}
) {
    Surface(
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = modifier
                .clickable { onClick(title) }
                .background(Color.White)
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .padding(16.dp)
               ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                tint = PrimaryBlue,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = title,
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.8f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                tint = Color.LightGray,
                contentDescription = null
            )
        }
    }
}