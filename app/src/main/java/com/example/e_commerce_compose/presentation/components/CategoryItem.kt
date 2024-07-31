package com.example.e_commerce_compose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onCategoryClick: (Category) -> Unit
) {
    Row(
        modifier = Modifier
            .then(
                if (isSelected) Modifier.background(Color.White)
                else Modifier
            )
            .height(70.dp)
            .padding(start = 1.dp , end = 2.dp)
            .padding(vertical = 2.dp)
            .fillMaxWidth()
            .clickable {
                onCategoryClick(category)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50.dp))
                    .padding(horizontal = 2.dp)
                    .width(6.dp)
                    .background(PrimaryBlue)
            )
        }
        Text(
            text = category.name?:"",
            modifier = Modifier.padding(start = 2.dp),
            color = PrimaryBlue,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
