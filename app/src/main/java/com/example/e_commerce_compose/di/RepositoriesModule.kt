package com.example.e_commerce_compose.di

import com.example.e_commerce_compose.data.repositoryImpl.CategoryRepositoryImpl
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoriesModule = module {
    single<CategoriesRepository> { CategoryRepositoryImpl(get()) }
    viewModel { CategoriesViewModel(get()) }
}