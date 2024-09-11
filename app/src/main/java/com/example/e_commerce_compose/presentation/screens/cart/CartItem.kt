package com.example.e_commerce_compose.presentation.screens.cart

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.e_commerce_compose.domain.model.Cart
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.presentation.components.CountBar
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.PrimaryText
import com.example.e_commerce_compose.ui.theme.poppins

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    product: Product,
    isLoading: Boolean = false,
    onRemoveClick: (id: String) -> Unit,
    onCountChange: (id: String, count: Int) -> Unit
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
        if (isLoading){
            Box(
                modifier = Modifier
                    .background(PrimaryBlue.copy(alpha = 0.2f))
                    .fillMaxSize()
            ){
                LinearProgressIndicator(
                    color = PrimaryBlue,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp)
                )
            }
        }
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
        Image(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "favorite",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
                .clickable {
                    onRemoveClick(product.id ?: "")
                }
        )
        CountBar(
            modifier = Modifier
                .size(width = 122.dp, height = 42.dp)
                .padding(end = 8.dp, bottom = 8.dp)
                .align(Alignment.BottomEnd),
            onCountChange = {
                onCountChange(product.id ?: "", it)
            },
            initialCount = product.count?:0,
            isPlusButtonEnabled = !isLoading,
            isMinusButtonEnabled = !isLoading || (product.count ?: 0) != 1
        )
    }
}