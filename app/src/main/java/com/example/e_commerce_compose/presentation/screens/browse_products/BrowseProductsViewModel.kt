package com.example.e_commerce_compose.presentation.screens.browse_products

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.domain.model.Product
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
    val categoryId = checkNotNull(savedStateHandle.get<String>("categoryId"))

    private val _state = MutableStateFlow(BrowseProductsState())
    val state = _state.asStateFlow()

    private val _effects = Channel<BrowseProductsEffects>()
    val effects = _effects.receiveAsFlow()

    init {
        onEvent(BrowseProductsEvents.FetchProducts)
    }
    fun onEvent(event: BrowseProductsEvents){
        when(event){
            BrowseProductsEvents.FetchProducts -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val fetchProducts = async { fetchProducts(
                        selectedCategoryId = categoryId
                    ) }
                    val fetchBrands = async { fetchBrands() }

                    awaitAll(fetchProducts, fetchBrands)
                }

            }
            is BrowseProductsEvents.ProductClicked -> {
                viewModelScope.launch {
                    _effects.send(BrowseProductsEffects.NavigateToProductDetails(event.productId))
                }
            }

            is BrowseProductsEvents.UpdateSelectedBrand -> {
                _state.update {
                    it.copy(
                        selectedBrandId = event.brandId
                    )
                }
            }
            is BrowseProductsEvents.UpdateShowFilters -> {
                _state.update {
                    it.copy(
                        showFilters = event.showFilters
                    )
                }
            }

            is BrowseProductsEvents.OnApplyFilters -> {
                viewModelScope.launch(Dispatchers.IO) {
                    fetchProducts(
                        selectedCategoryId = event.categoryId,
                        minPrice = event.minPrice,
                        sort = event.sort
                    )
                }
            }

            is BrowseProductsEvents.UpdateSelectedSort -> {
                _state.update {
                    it.copy(
                        selectedSort = event.sort
                    )
                }
            }
        }
    }

    private suspend fun fetchBrands(){
        productsRepository.getAllBrands().collect{result->
            when(result){
                is Resource.Error ->{
                    _state.update {
                        it.copy(
                            fetchBrandsError = result.error.message
                        )
                    }
                }
                Resource.Loading -> {
                    _state.update {
                        it.copy(
                            fetchBrandsLoading = true
                        )
                    }
                }
                is Resource.ServerError -> {
                    _state.update {
                        it.copy(
                            fetchBrandsError = result.error.message
                        )
                    }
                }
                is Resource.Success -> {
                    result.data?.let { brands ->
                        _state.update {
                            it.copy(
                                brands = brands.let { list ->
                                    list.mapNotNull { brand ->
                                        brand
                                    }
                                },
                                fetchBrandsLoading = false
                            )
                        }
                    }
                }
            }

        }
    }

    private suspend fun fetchProducts(
        selectedCategoryId: String? = null,
        minPrice: Int? = null,
        sort: String? = null
    ) {
        productsRepository.getProductsByCategoryId(
            categoryId = selectedCategoryId,
            brand = _state.value.selectedBrandId,
            minPrice = minPrice,
            sort = sort
        )
            .collect{result->
            when(result){
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.error.message,
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
                            error = result.error.message
                        )
                    }
                }
                is Resource.Success -> {
                   result.data?.let {products->
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
                                       Log.d("product in viewmodel", product.toString())
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
