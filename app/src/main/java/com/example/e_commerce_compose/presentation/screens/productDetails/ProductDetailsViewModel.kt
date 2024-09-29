package com.example.e_commerce_compose.presentation.screens.productDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.data.model.AddToWishlistRequest
import com.example.e_commerce_compose.domain.repository.CartRepository
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.domain.repository.WishListRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository,
    private val wishListRepository: WishListRepository,
    private val cartRepository: CartRepository
): ViewModel() {
    val productId: String = checkNotNull(savedStateHandle["productId"])

    private val _state = MutableStateFlow(ProductDetailsState())
    val state = _state.asStateFlow()

    private val _effect = Channel<ProductDetailsEffects>()
    val effect = _effect.receiveAsFlow()

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
            is ProductsDetailsEvents.AddToCart -> handleAddToCart(event.productId)
            is ProductsDetailsEvents.RemoveFromCart -> handleRemoveFromCart(event.productId)
        }
    }

    private fun handleRemoveFromCart(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.removeProductFromCart(productId).collect{result->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.message.toString(),
                                cartOperationLoading = false
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                cartOperationLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                product = _state.value.product?.copy(isInCart = false),
                                cartOperationLoading = false
                            )
                        }
                        _effect.send(ProductDetailsEffects.ShowToast("Product removed from cart"))
                    }
                }
            }
        }
    }

    private fun handleAddToCart(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            cartRepository.addProductToCart(productId).collect{result->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.message.toString(),
                                cartOperationLoading = false
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                cartOperationLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                product = _state.value.product?.copy(isInCart = true),
                                cartOperationLoading = false
                            )
                        }

                        _effect.send(ProductDetailsEffects.ShowToast("Product added to cart successfully"))

                    }
                }
            }

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
                        _effect.send(ProductDetailsEffects.ShowToast("Product removed from wishlist"))
                    }
                }
            }
        }
    }

    private fun handleAddToWishlist(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            wishListRepository.addProductToWishList(
                AddToWishlistRequest(
                productId = productId
            )
            ).collect{ result->
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
                                    product = _state.value.product?.copy(isFavorite = true),
                                    wishlistOperationLoading = false
                                )
                            }
                            _effect.send(ProductDetailsEffects.ShowToast("Product added to wishlist"))
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