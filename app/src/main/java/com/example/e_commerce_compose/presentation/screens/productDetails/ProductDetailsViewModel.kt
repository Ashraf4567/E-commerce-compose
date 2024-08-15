package com.example.e_commerce_compose.presentation.screens.productDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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