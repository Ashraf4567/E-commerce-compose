package com.example.e_commerce_compose.presentation.screens.categories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.data.toDomain
import com.example.e_commerce_compose.data.toDomainModel
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
                    it.copy(selectedCategory = categoriesEvents.category)
                }
                getSubCategory()
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
                                categories = result.data?.map { categoryDto-> categoryDto?.toDomainModel() },
                                selectedCategory = result.data?.get(1)?.toDomainModel(),
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
                                subCategories = result.data?.map { subCategoryDto-> subCategoryDto?.toDomain() },
                                subCategoriesLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


}