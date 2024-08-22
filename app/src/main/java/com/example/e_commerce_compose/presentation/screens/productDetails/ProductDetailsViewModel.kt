package com.example.e_commerce_compose.presentation.screens.productDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.domain.model.AddToWishlistRequest
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository
): ViewModel() {
    val productId: String = checkNotNull(savedStateHandle["productId"])

    private val _state = MutableStateFlow(ProductDetailsState())
    val state = _state.asStateFlow()

    init {
        onEvent(ProductsDetailsEvents.LoadProductDetails)
    }

    fun onEvent(event: ProductsDetailsEvents) {
        when(event){
            ProductsDetailsEvents.LoadProductDetails -> {
                handleLoadProductDetails()
            }

            is ProductsDetailsEvents.AddToWishlist -> {
                handleAddToWishlist(event.productId)
            }

            is ProductsDetailsEvents.RemoveFromWishlist -> handleRemoveFromWishlist(event.productId)
        }
    }

    private fun handleRemoveFromWishlist(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            productsRepository.removeProductFromWishList(productId).collect{result->
                when(result){
                    is Resource.Error -> {}
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                wishlistOperationLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                product = _state.value.product?.copy(isFavorite = false),
                                wishlistOperationLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleAddToWishlist(productId: String) {
        Log.d("test add to wishlist", productId)
        viewModelScope.launch(Dispatchers.IO) {
            productsRepository.addProductToWishList(AddToWishlistRequest(
                productId = productId
            )).collect{ result->
                when(result){
                    is Resource.Error -> {
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                wishlistOperationLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        val myProduct = result.data?.firstOrNull{
                            it == productId
                        }
                        myProduct.let {
                            _state.update {state->
                                state.copy(
                                    product = _state.value.product?.copy(isFavorite = false),
                                    wishlistOperationLoading = false
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    private fun handleLoadProductDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            productsRepository.getProductDetailsById(productId).collect{result->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.message.toString(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {
                        _state.update {
                            it.copy(
                                error = result.error.message.toString(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                product = result.data,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


}