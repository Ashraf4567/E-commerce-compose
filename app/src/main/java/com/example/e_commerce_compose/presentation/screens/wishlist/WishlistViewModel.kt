package com.example.e_commerce_compose.presentation.screens.wishlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.domain.repository.CartRepository
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.domain.repository.WishListRepository
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsEffects
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val wishListRepository: WishListRepository,
    private val cartRepository: CartRepository
): ViewModel() {

    private val _state = MutableStateFlow(WishlistState())
    val state = _state.asStateFlow()

    init {
        onEvent(WishlistEvents.LoadWishlistProducts)
    }

    fun onEvent(event: WishlistEvents){
        when(event){
            WishlistEvents.LoadWishlistProducts -> handleLoadWishlistProducts()
            is WishlistEvents.AddToCart -> handleAddToCart(event.productId)
            WishlistEvents.ClearWishlist -> handleClearWishlist()
            is WishlistEvents.RemoveFromCart -> {}
            is WishlistEvents.RemoveFromWishlist -> handleRemoveFromWishlist(event.productId)
        }
    }

    private fun handleRemoveFromWishlist(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            wishListRepository.removeProductFromWishList(productId).collect{result->
                when(result){
                    is Resource.Error -> {}
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                specificProductWishlistOperationLoading = Pair(productId, true)
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                specificProductWishlistOperationLoading = Pair(productId, false),
                                wishlistItems = _state.value.wishlistItems.filter {product->
                                    product.id != productId
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleClearWishlist() {

    }

    private fun handleAddToCart(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            cartRepository.addProductToCart(productId).collect{result->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.message.toString(),
                                specificProductCartOperationLoading = Pair(productId, false)
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                specificProductCartOperationLoading = Pair(productId, true)
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                specificProductCartOperationLoading = Pair(productId, false),
                                wishlistItems = _state.value.wishlistItems.map {product->
                                    if (product.id == productId){
                                        product.copy(isInCart = true)
                                    }else{
                                        product
                                    }
                                }
                            )
                        }

                        //_effect.send(ProductDetailsEffects.ShowToast("Product added to cart successfully"))

                    }
                }
            }

        }
    }

    private fun handleLoadWishlistProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            wishListRepository.getWishlist().collect{result->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.message.toString(),
                                isLoading = false
                            )
                        }
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