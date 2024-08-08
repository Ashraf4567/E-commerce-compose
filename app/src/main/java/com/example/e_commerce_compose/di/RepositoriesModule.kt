package com.example.e_commerce_compose.di

import com.example.e_commerce_compose.data.repositoryImpl.CategoryRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.ProductsRepositoryImpl
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesViewModel
import com.example.e_commerce_compose.presentation.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoriesModule = module {
    single<CategoriesRepository> { CategoryRepositoryImpl (get()) }
    single<ProductsRepository> { ProductsRepositoryImpl(get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { HomeViewModel(get() , get()) }
}