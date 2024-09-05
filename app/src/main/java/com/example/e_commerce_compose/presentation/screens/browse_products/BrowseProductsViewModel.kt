package com.example.e_commerce_compose.presentation.screens.browse_products

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrowseProductsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository
): ViewModel(){
    private val categoryId = checkNotNull(savedStateHandle.get<String>("categoryId"))

    private val _state = MutableStateFlow(BrowseProductsState())
    val state = _state.asStateFlow()

    private val _effects = Channel<BrowseProductsEffects>()
    val effects = _effects.receiveAsFlow()

    init {
        onEvent(BrowseProductsEvents.FetchProducts)
    }
    fun onEvent(event: BrowseProductsEvents){
        when(event){
            BrowseProductsEvents.FetchProducts -> fetchProducts()
            is BrowseProductsEvents.ProductClicked -> {
                viewModelScope.launch {
                    _effects.send(BrowseProductsEffects.NavigateToProductDetails(event.productId))
                }
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productsRepository.getProductsByCategoryId(categoryId = categoryId)
                .collect{resutl->
                when(resutl){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = resutl.error.message,
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
                    is Resource.ServerError -> {
                        _state.update {
                            it.copy(
                                error = resutl.error.message
                            )
                        }
                    }
                    is Resource.Success -> {
                       resutl.data?.let {products->
                           if (products.isEmpty()){
                               _state.update {
                                   it.copy(
                                       isEmptyList = true,
                                       isLoading = false
                                   )
                               }
                           }else{
                               _state.update {
                                   it.copy(
                                       products = products.mapNotNull { product->
                                           product
                                       },
                                       isLoading = false,
                                       screenTitle = products.firstOrNull()?.category?.name?:""
                                   )
                               }
                           }
                       }
                    }
                }
            }
        }
    }
}
