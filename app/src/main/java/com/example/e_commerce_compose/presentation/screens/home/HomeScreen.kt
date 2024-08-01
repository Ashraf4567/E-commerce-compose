package com.example.e_commerce_compose.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.presentation.components.MyTopAppBar

val bannersList = listOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            MyTopAppBar(modifier = Modifier.padding(top = 25.dp) , onQueryChange = {})
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding() + 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Banner()
        }
    }
}