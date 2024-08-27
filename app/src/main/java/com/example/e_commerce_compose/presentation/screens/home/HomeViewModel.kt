package com.example.e_commerce_compose.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.data.local.DataStoreManager
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
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

class HomeViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val productsRepository: ProductsRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private val _effect = Channel<HomeEffects>()
    val effect = _effect.receiveAsFlow()

     fun onEvent(event: HomeEvents , onProductClicked : (productId : String) -> Unit) {
        when(event) {
            HomeEvents.LoadData -> {
                handleLoadData()
            }

            is HomeEvents.OnProductClicked -> {
                onProductClicked(event.productId)
            }

            HomeEvents.NavigateToCart -> {
                viewModelScope.launch {
                    _effect.send(HomeEffects.NavigateToCart)
                }
            }
        }
    }

    private fun handleLoadData() {
        if (_state.value.categoriesList?.isNotEmpty()!! || _state.value.productList?.isNotEmpty()!!) return
        viewModelScope.launch(Dispatchers.IO) {
            val categoriesDeferred = async { loadCategories() }
            val productsDeferred = async { loadProducts() }
            dataStoreManager.getUserFromDataStore().collect{user ->
                _state.update {
                    it.copy(
                        currentUserName = user.name
                    )
                }
                Log.d("HomeViewModel", "handleLoadData: $user")
            }
            try {
                awaitAll(categoriesDeferred, productsDeferred)
            } catch (e: Exception) {
                // Handle the error
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private suspend fun loadCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            categoriesRepository.getCategories().collect{result ->
                when(result){
                    is Resource.Error -> {}
                    is Resource.Loading -> {
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
                                categoriesList = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadProducts(categoryId: String = "6439d2d167d9aa4ca970649f"){
        viewModelScope.launch(Dispatchers.IO) {
            productsRepository.getProductsByCategoryId(categoryId)
                .collect{result ->
                    when(result){
                        is Resource.Error -> {}
                        is Resource.Loading -> {
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
                                    productList = result.data?.filterNotNull(),
                                    isLoading = false
                                )
                            }
                        }
                    }

            }
        }
    }
}