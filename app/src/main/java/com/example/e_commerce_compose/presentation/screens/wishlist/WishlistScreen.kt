package com.example.e_commerce_compose.presentation.screens.wishlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.presentation.components.MyTopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Wishlist(
    modifier: Modifier = Modifier,
    wishlistState: WishlistState,
    onEvent: (WishlistEvents) -> Unit
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                modifier = Modifier.padding(top = 35.dp),
                showGreeting = false,
                onQueryChange = {}
            )
        }
    ) {
        if (wishlistState.isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        // If the wishlist is empty
        else if (wishlistState.wishlistItems.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text("Your wishlist is empty")
            }
        }else if (wishlistState.error.isNotEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column {
                    Text(text = wishlistState.error)
                    Button(onClick ={
                        onEvent(WishlistEvents.LoadWishlistProducts)
                    } ) {
                        Text(text = "Retry")
                    }
                }
            }
        }

        else if (wishlistState.wishlistItems.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = it.calculateTopPadding()+10.dp , start = 10.dp , end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            items(wishlistState.wishlistItems.size){index->
                ItemWishlist(
                    product = wishlistState.wishlistItems[index],
                    isCartOperationLoading = wishlistState
                        .specificProductCartOperationLoading.first == wishlistState.wishlistItems[index].id && wishlistState.specificProductCartOperationLoading.second,
                    isWishlistOperationLoading = wishlistState
                        .specificProductWishlistOperationLoading.first == wishlistState.wishlistItems[index].id && wishlistState.specificProductWishlistOperationLoading.second,
                    onAddToCart = {productId->
                        onEvent(
                            WishlistEvents.AddToCart(productId)
                        )
                    },
                    onRemoveFromWishlist = {
                        onEvent(
                            WishlistEvents.RemoveFromWishlist(wishlistState.wishlistItems[index].id?:"")
                        )
                    }
                )
            }
        }
    }

    }
}