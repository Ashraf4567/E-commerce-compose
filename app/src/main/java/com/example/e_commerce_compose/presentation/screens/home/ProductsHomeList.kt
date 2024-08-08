package com.example.e_commerce_compose.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.presentation.components.HomeProductsItem

@Composable
fun ProductsHomeList(
    modifier: Modifier = Modifier,
    products: List<Product?>?
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(products?.size!!) {
            HomeProductsItem(product = products[it]!!)
        }
    }
}