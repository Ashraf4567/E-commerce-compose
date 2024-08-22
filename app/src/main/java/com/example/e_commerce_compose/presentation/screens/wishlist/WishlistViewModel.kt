package com.example.e_commerce_compose.presentation.screens.wishlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val productsRepository: ProductsRepository
): ViewModel() {

    private val _state = MutableStateFlow(WishlistState())
    val state = _state.asStateFlow()

    init {
        onEvent(WishlistEvents.LoadWishlistProducts)
    }

    fun onEvent(event: WishlistEvents){
        when(event){
            WishlistEvents.LoadWishlistProducts -> handleLoadWishlistProducts()
        }
    }

    private fun handleLoadWishlistProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productsRepository.getWishlist().collect{result->
                Log.d("WishlistViewModel", "handleLoadWishlistProducts: $result")
                when(result){
                    is Resource.Error -> {
                        Log.d("WishlistViewModel", "handleLoadWishlistProducts: ${result.error.message}")
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                wishlistItems = result.data?.mapNotNull {product->
                                    product
                                } ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }

            }
        }
    }
}