package com.example.e_commerce_compose.presentation.screens.cart

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_compose.presentation.components.LabeledOutlinedTextFiled
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsTopBar
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.PrimaryText
import com.example.e_commerce_compose.ui.theme.poppins
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel,
    onBackClicked: () -> Unit,
    onCheckoutClicked: () -> Unit
) {
    val state by cartViewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        cartViewModel.effect.collect{effect ->
            when(effect){
                CartEffects.NavigateBack -> onBackClicked()
                CartEffects.NavigateToCheckout -> onCheckoutClicked()
                is CartEffects.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            ProductDetailsTopBar(
                onBackClicked = { cartViewModel.onEvent(CartEvents.BackPressed) },
                title = "Cart",
                scrollBehavior = scrollBehavior,
                showCartIcon = false
            )
        }
    ) {paddingValues ->
        if (state.isLoading){
            Box(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = PrimaryBlue
                )
            }
        }else if (state.error.isBlank() && !state.cart.products.isNullOrEmpty()){
            Box(
                modifier = modifier
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = modifier
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.cart.products.let {
                        items(
                            count = it?.size?:0 ,
                            key = {
                                    index ->
                                it?.get(index)?.id!!
                            }
                        ){index ->
                            CartItem(
                                product = state.cart.products!![index]!!,
                                onRemoveClick = {productId ->
                                    cartViewModel.onEvent(CartEvents.RemoveItem(productId))
                                },
                                onCountChange = {id , count ->
                                    cartViewModel.onEvent(CartEvents.UpdateProductCount(id,count))
                                }
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 36.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "Total Price",
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "EGP ${state.cart.totalCartPrice}",
                            fontFamily = poppins,
                            color = PrimaryText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )


                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier = Modifier.size(height = 48.dp, width = 250.dp),
                        onClick = { cartViewModel.onEvent(CartEvents.CheckoutPressed) }
                    ) {
                        Text(text = "Checkout")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Outlined.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.width(24.dp)
                        )
                    }
                }
            }

        }else if(state.cart.products.isNullOrEmpty()){
            Box(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
                ) {
                Text(text = "Your cart is empty")
            }
        }

    }
}