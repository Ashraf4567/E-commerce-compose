package com.example.e_commerce_compose.presentation.screens.wishlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.PrimaryText
import com.example.e_commerce_compose.ui.theme.poppins

@Composable
fun ItemWishlist(
    modifier: Modifier = Modifier,
    product: Product,
    onAddToCart: (id: String) -> Unit = {},
    onRemoveFromWishlist: () -> Unit = {},
    isCartOperationLoading: Boolean = false,
    isWishlistOperationLoading: Boolean = false
) {
    Box(
        modifier = modifier
            .size(width = 398.dp, height = 113.dp)
            .clip(RoundedCornerShape(15.dp))
            .padding(end = 5.dp)
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = product.imageCover,
                contentDescription = "product_image",
                modifier = Modifier
                    .size(
                        width = 120.dp,
                        height = 113.dp
                    )
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = product.title?.take(12)+"...",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PrimaryText,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Text(
                    text = "EGP ${product.price}",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
        if (!isWishlistOperationLoading) {
            Image(
                painter = painterResource(id = R.drawable.ic_wishlist_filled),
                contentDescription = "favorite",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        onRemoveFromWishlist()
                    }
            )
        }else{
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp)
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp , top = 8.dp),
                color = PrimaryBlue,
                strokeWidth = 2.dp
            )
        }
        Button(
            onClick = { onAddToCart(product.id?:"") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(width = 120.dp, height = 40.dp)
                .padding(end = 8.dp, bottom = 8.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                disabledContainerColor = Color.LightGray
            ),
            enabled = !isCartOperationLoading,
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 10.dp,
                vertical = 4.dp
            )
        ) {
            AnimatedVisibility(visible = isCartOperationLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Text(text = if (product.isInCart) "remove from cart" else "Add to Cart" , fontFamily = poppins , fontSize = if(product.isInCart) 8.sp else 12.sp)
        }
    }
}