package com.example.e_commerce_compose.di

import androidx.lifecycle.SavedStateHandle
import com.example.e_commerce_compose.data.repositoryImpl.AuthRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.CategoryRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.ProductsRepositoryImpl
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesViewModel
import com.example.e_commerce_compose.presentation.screens.home.HomeViewModel
import com.example.e_commerce_compose.presentation.screens.login.SignInViewModel
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoriesModule = module {
    single<CategoriesRepository> { CategoryRepositoryImpl (get()) }
    single<ProductsRepository> { ProductsRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get() , get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { HomeViewModel(get() , get()) }
    viewModel { ProductDetailsViewModel(get() , get()) }
    viewModel{SignInViewModel(get())}
}