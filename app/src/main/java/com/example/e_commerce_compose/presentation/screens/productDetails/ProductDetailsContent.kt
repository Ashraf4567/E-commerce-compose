package com.example.e_commerce_compose.presentation.screens.productDetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.e_commerce_compose.presentation.components.CountBar
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.PrimaryText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsContent(product: Product) {

    val pagerState = rememberPagerState {
        product.images?.size ?: 0
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            HorizontalPager(state = pagerState) {
                AsyncImage(
                    model = product.images?.get(it),
                    contentDescription = "product image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.5.dp,
                            color = PrimaryBlue.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        )
                )
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp)
                ,
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount){
                    val color = if (pagerState.currentPage == it) PrimaryBlue else PrimaryBlue.copy(alpha = 0.3f)
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)

                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${
                    product.title?.dropLast(product.title.length.minus(10) ?: 0)
                }...",
                fontSize = 18.sp,
                color = PrimaryText,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "EGP ${product.price}",
                fontSize = 18.sp,
                color = PrimaryText,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.sold.toString() + " Sold",
                fontSize = 12.sp,
                color = PrimaryText,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = PrimaryBlue.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "star"
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = product.ratingsAverage.toString(),
                fontSize = 13.sp,
                color = PrimaryText
            )
            Text(
                text = " (${product.ratingsQuantity})",
                fontSize = 13.sp,
                color = PrimaryText
            )
            Spacer(modifier = Modifier.width(40.dp))
            CountBar(Modifier)
        }
    }
}