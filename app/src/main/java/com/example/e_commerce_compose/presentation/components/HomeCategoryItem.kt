package com.example.e_commerce_compose.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

@Composable
fun HomeCategoryItem(
    category: Category,
    modifier: Modifier,
    onCategoryClicked: (String) -> Unit = {}
) {

    val words = category.name?.split(" " , limit = 1)
    Column(
        modifier = modifier.width(100.dp).height(144.dp).clickable(
            indication = rememberRipple(),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onCategoryClicked(category.id?:"")
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.image)
                .crossfade(true)
                .build()
            ,
            contentScale = ContentScale.Crop,
            contentDescription = "category image",
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .size(100.dp, 100.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = PrimaryBlue,
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.height(8.dp))

        //here i want if word more than 1 word then show them in new line
        category.name?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 11.sp,
                color = PrimaryBlue
            )
        }
    }
}
