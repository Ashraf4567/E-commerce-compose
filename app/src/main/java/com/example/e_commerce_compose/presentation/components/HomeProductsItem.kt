package com.example.e_commerce_compose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.ui.theme.Orange
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.PrimaryText


@Composable
fun HomeProductsItem(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (productID: String) -> Unit
) {

    val favoriteIcon = if (product.isFavorite) painterResource(id = R.drawable.ic_wishlist_filled)else painterResource(id = R.drawable.ic_add_to_wishlis)

    Box(
        modifier = modifier
            .width(160.dp)
            .height(190.dp)
            .border(
                width = 1.5.dp,
                color = PrimaryBlue.copy(alpha = 0.3f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick(product.id ?: "") }
    ) {
        Column {
            AsyncImage(
                model =  product.imageCover,
                contentDescription = "product image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                placeholder = painterResource(id = R.drawable.placeholder),

            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxHeight()
                ,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = product.title?:"",
                    fontSize = 12.sp,
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Review (${product.ratingsAverage})",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = PrimaryText
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
                        contentDescription = "star",
                        tint = Orange,
                        modifier = Modifier.size(15.dp)
                    )
                }
                Text(
                    text = "EGP ${product.price}",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = PrimaryText
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_add_home),
            contentDescription = "add",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 6.dp, end = 6.dp)
                .clip(CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = PrimaryBlue.copy(alpha = 0.3f),
                        bounded = true
                    )
                ) { }
        )

        Image(
            painter = favoriteIcon,
            contentDescription = "add",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 3.dp, end = 3.dp)
                .size(35.dp)
                .clip(CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = PrimaryBlue.copy(alpha = 0.3f),
                        bounded = true
                    )
                ) { }
        )


    }
}