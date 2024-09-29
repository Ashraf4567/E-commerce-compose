package com.example.e_commerce_compose.presentation.screens.profile.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce_compose.data.model.cart.CartProductsItem
import com.example.e_commerce_compose.data.model.order.UserOrdersResponseItem

@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    orderUserOrdersResponseItem: UserOrdersResponseItem,
    backgroundColor: Color = Color.LightGray
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        orderUserOrdersResponseItem.cartItems?.forEach { cartProductsItem ->
            if (cartProductsItem != null) {
                ProductOrderItem(product =  cartProductsItem)
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
        Text(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth(),
            text = "Total "+ orderUserOrdersResponseItem.totalOrderPrice
        )
    }
}

@Composable
fun ProductOrderItem(modifier: Modifier = Modifier , product: CartProductsItem) {
    Row(
        modifier = modifier
            .size(width = 398.dp, height = 113.dp)
            .clip(RoundedCornerShape(15.dp))
            .padding(end = 5.dp)
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.product?.imageCover,
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
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = product.product?.title?:"" , maxLines = 1)
            Text(text = product.price.toString()+" EGP")
        }
        Text(
            text = "count ${product.count}",
            modifier = Modifier
                .padding(8.dp)

        )
    }
}