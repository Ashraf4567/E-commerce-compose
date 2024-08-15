package com.example.e_commerce_compose.presentation.screens.productDetails

sealed class ProductsDetailsEvents {
    data object LoadProductDetails : ProductsDetailsEvents()
}