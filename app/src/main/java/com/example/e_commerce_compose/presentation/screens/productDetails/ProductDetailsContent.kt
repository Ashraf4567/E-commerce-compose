package com.example.e_commerce_compose.presentation.screens.productDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.presentation.components.AddToCartButton
import com.example.e_commerce_compose.presentation.components.ColorBalletSelector
import com.example.e_commerce_compose.presentation.components.CountBar
import com.example.e_commerce_compose.presentation.components.ExpandedText
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.PrimaryText
import com.example.e_commerce_compose.ui.theme.poppins

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsContent(
    state: ProductDetailsState,
    scrollBehavior: TopAppBarScrollBehavior,
    onEvent: (ProductsDetailsEvents) -> Unit
) {
    val product = state.product ?: return
    var countToAddToCart by remember {
        mutableIntStateOf(1)
    }

    val pagerState = rememberPagerState {
        product.images?.size ?: 0
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
            .background(Color.White)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .border(
                            width = 1.5.dp,
                            color = PrimaryBlue.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    HorizontalPager(state = pagerState) {
                        AsyncImage(
                            model = product.images?.get(it),
                            contentDescription = "product image",
                            modifier = Modifier
                                .fillMaxWidth()

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
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "EGP ${product.price}",
                        fontSize = 18.sp,
                        color = PrimaryText,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
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
                    CountBar(
                        Modifier ,
                        onCountChange = {
                            countToAddToCart = it
                        }
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    color = PrimaryText,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                ExpandedText(
                    text = product.description?.take(100) ?: "",
                    expandedText = product.description ?: "",
                    expandedTextButton = "Read More",
                    shrinkTextButton = "Read Less"
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Color",
                    fontSize = 18.sp,
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                ColorBalletSelector()


            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedVisibility(
                visible = state.wishlistOperationLoading,
                enter  = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ){
                CircularProgressIndicator()
            }
            if (product.isFavorite){
                Image(
                    painter = painterResource(id = R.drawable.ic_wishlist_filled),
                    contentDescription = "favorite",
                    modifier = Modifier
                        .size(70.dp)
                        .clickable {
                            onEvent(
                                ProductsDetailsEvents.RemoveFromWishlist(product.id?:"")
                            )
                        }
                )
            }
            if (!product.isFavorite){
                Image(
                    painter = painterResource(id = R.drawable.ic_add_to_wishlis),
                    contentDescription = "favorite",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            onEvent(
                                ProductsDetailsEvents.AddToWishlist(product.id?:"")
                            )
                        }
                )
            }

            AddToCartButton(
                modifier = Modifier
                    .clickable {
                        if (product.isInCart){
                            onEvent(ProductsDetailsEvents.RemoveFromCart(productId = product.id?:""))
                        }else{
                            onEvent(ProductsDetailsEvents.AddToCart(product.id?:"" , countToAddToCart))
                        }
                    },
                isLoading = state.cartOperationLoading,
                isInCart = product.isInCart
            )
        }
    }

}