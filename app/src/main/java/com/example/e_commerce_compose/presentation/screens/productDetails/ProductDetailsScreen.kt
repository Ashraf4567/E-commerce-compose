package com.example.e_commerce_compose.presentation.screens.productDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailsScreen(
    state: ProductDetailsState,
    onBackClick: () -> Unit,
) {

    Scaffold(
        topBar = { ProductDetailsTopBar(
            onBackClicked = onBackClick
        )}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading){
                CircularProgressIndicator()
            }
            if (state.error.isNotBlank()){
                Text(text = state.error)
            }
            if (state.product != null){
                ProductDetailsContent(product = state.product)
            }

        }
    }
}