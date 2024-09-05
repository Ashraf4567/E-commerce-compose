package com.example.e_commerce_compose.presentation.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    val state = MutableStateFlow(CategoriesState())

    private val _effect = MutableSharedFlow<CategoriesEffects>()
    val effect = _effect.asSharedFlow()

    init {
        onEvent(CategoriesEvents.GetCategories)
    }
    fun onEvent(categoriesEvents: CategoriesEvents) {
        when(categoriesEvents){
            is CategoriesEvents.CategoryClicked -> {
                state.update {
                    it.copy(selectedCategory = categoriesEvents.category)
                }
                getSubCategory()
            }
            CategoriesEvents.GetCategories -> {
                getCategories()
            }

            is CategoriesEvents.SubCategoryClicked -> {
                viewModelScope.launch {
                    _effect.emit(CategoriesEffects.NavigateToProducts(categoriesEvents.categoryId))
                }
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
                                categoriesLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        state.update {
                            it.copy(categoriesLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        state.update {
                            it.copy(
                                categories = result.data,
                                selectedCategory = result.data?.get(1),
                                categoriesLoading = false
                            )
                        }
                        onEvent(CategoriesEvents.CategoryClicked(state.value.selectedCategory!!))
                    }

                    is Resource.ServerError -> {
                        state.update {
                            it.copy(
                                error = result.error.message,
                                categoriesLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getSubCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesRepository.getSubCategories(state.value.selectedCategory?.id?:"").collect{result ->
                when(result){
                    is Resource.Error -> {
                        state.update {
                            it.copy(
                                error = result.error.message,
                                subCategoriesLoading = false
                            )
                        }
                    }
                    Resource.Loading -> {
                        state.update {
                            it.copy(subCategoriesLoading = true)
                        }
                    }
                    is Resource.ServerError -> {
                        state.update {
                            it.copy(
                                error = result.error.message,
                                subCategoriesLoading = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        state.update {
                            it.copy(
                                subCategories = result.data,
                                subCategoriesLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


}