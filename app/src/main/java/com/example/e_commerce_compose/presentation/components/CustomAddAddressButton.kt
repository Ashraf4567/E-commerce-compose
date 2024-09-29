package com.example.e_commerce_compose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

@Preview(showBackground = true)
@Composable
fun CustomAddAddressButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    val stroke = Stroke(
        width = 5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.LightGray)
            .clickable { onClick() }
            .drawBehind {
                drawRoundRect(
                    color = PrimaryBlue,
                    style = stroke,
                    cornerRadius = CornerRadius(30f)
                )
            }
            .padding(vertical = 20.dp)
            .fillMaxWidth()
            ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",
            tint = PrimaryBlue
        )
        Text(
            text = "Add New Sipping Address",
            style = MaterialTheme.typography.titleMedium.copy(color = PrimaryBlue)
        )
    }
}