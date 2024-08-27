package com.example.e_commerce_compose.presentation.screens.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.domain.model.Cart
import com.example.e_commerce_compose.domain.repository.CartRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
): ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    private val _effect = Channel<CartEffects>()
    val effect = _effect.receiveAsFlow()

    init {
        onEvent(CartEvents.LoadCart)
    }

    fun onEvent(event: CartEvents){
        when(event){
            CartEvents.LoadCart -> loadCart()
            is CartEvents.RemoveItem -> handleRemoveItem(event.id)
            is CartEvents.UpdateProductCount -> handleUpdateProductCount(event.id, event.count)
            CartEvents.BackPressed -> {
                viewModelScope.launch {
                    _effect.send(CartEffects.NavigateBack)
                }
            }
        }
    }

    private fun handleUpdateProductCount(id: String, count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateProductCount(id, count).collect{res ->
                when(res){
                    is Resource.Error ->{
                        _state.update {
                            it.copy(
                                cart = it.cart.copy(products = it.cart.products?.map { product ->
                                    if (product?.id == id){
                                        product.copy(isLoading = false)
                                    }else{
                                        product
                                    }
                                }),
                                error = res.error.message.toString()
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                cart = _state.value.cart.copy(
                                    products = it.cart.products?.map { product ->
                                    if (product?.id == id){
                                        product.copy(isLoading = true)
                                    }else{
                                        product
                                    }
                                }
                                )
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        // here will get new product to replace with the old and will check by id
                        val newProduct = res.data?.data?.products?.first {
                            it?.product?.id == id
                        }?.mapToDomain()
                        _state.update {
                            it.copy(
                                cart = it.cart.copy(
                                    products = it.cart.products?.map { product ->
                                    if (product?.id == id){
                                        newProduct
                                    }else{
                                        product
                                    }
                                },
                                    totalCartPrice = res.data?.data?.totalCartPrice
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleRemoveItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.removeProductFromCart(id).collect{ res ->
                when(res){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                cart = it.cart.copy(
                                    products = it.cart.products?.map { product ->
                                        if (product?.id == id){
                                            product.copy(isLoading = false)
                                        }else{
                                            product
                                        }
                                    }
                                ),
                                error = res.error.message.toString()
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                cart = it.cart.copy(
                                    products = it.cart.products?.map { product ->
                                        if (product?.id == id){
                                            product.copy(isLoading = true)
                                        }else{
                                            product
                                        }
                                    }
                                )
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                cart = Cart(
                                    cartOwner = res.data?.data?.cartOwner,
                                    createdAt = res.data?.data?.createdAt,
                                    id = res.data?.data?.id,
                                    products = res.data?.data?.products?.map {product->
                                        product?.mapToDomain()
                                    },
                                    totalCartPrice = res.data?.data?.totalCartPrice,
                                    updatedAt = res.data?.data?.updatedAt
                                )
                            )
                        }
                        _effect.send(CartEffects.ShowToast("Product removed from cart successfully"))
                    }
                }
            }
        }
    }

    private fun loadCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.getCart().collect { res ->
                Log.d("CartViewModel", "loadCart: $res")
                when(res){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = res.error.message.toString()
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
                    is Resource.ServerError -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = res.error.message.toString()
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                cart = Cart(
                                    cartOwner = res.data?.data?.cartOwner,
                                    createdAt = res.data?.data?.createdAt,
                                    id = res.data?.data?.id,
                                    products = res.data?.data?.products?.map {
                                        it?.mapToDomain()
                                    },
                                    totalCartPrice = res.data?.data?.totalCartPrice,
                                    updatedAt = res.data?.data?.updatedAt
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}