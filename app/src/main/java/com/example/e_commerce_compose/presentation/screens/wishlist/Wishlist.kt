package com.example.e_commerce_compose.presentation.screens.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.presentation.components.MyTopAppBar

@Composable
fun Wishlist(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            MyTopAppBar(modifier = Modifier.padding(top = 25.dp) , onQueryChange = {})
        }
    ) {

    }
}