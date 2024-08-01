package com.example.e_commerce_compose.presentation.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    val state = MutableStateFlow(CategoriesState())

    init {
        onEvent(CategoriesEvents.GetCategories)
    }
    fun onEvent(categoriesEvents: CategoriesEvents) {
        when(categoriesEvents){
            is CategoriesEvents.CategoryClicked -> {
                state.update {
                    it.copy(selectedCategoryId = categoriesEvents.id)
                }
            }
            CategoriesEvents.GetCategories -> {
                getCategories()
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesRepository.getCategories().collect{result ->
                when(result){
                    is Resource.Error -> {
                        state.update {
                            it.copy(
                                error = result.error.message,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        state.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        state.update {
                            it.copy(
                                categories = result.data,
                                selectedCategoryId = result.data?.get(0)?.id,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.ServerError -> {
                        state.update {
                            it.copy(
                                error = result.error.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }




}