package com.example.e_commerce_compose.presentation.screens.browse_products

sealed class BrowseProductsEvents {
    data object FetchProducts : BrowseProductsEvents()
    data class ProductClicked(val productId: String) : BrowseProductsEvents()
    data class UpdateShowFilters(val showFilters: Boolean) : BrowseProductsEvents()
    data class UpdateSelectedBrand(val brandId: String) : BrowseProductsEvents()
    data class OnApplyFilters(
        val categoryId: String?,
        val brandId: String?,
        val minPrice: Int?,
        val sort: String?
    ) : BrowseProductsEvents()

    data class UpdateSelectedSort(val sort: String) : BrowseProductsEvents()

}