package com.example.e_commerce_compose.di

import com.example.e_commerce_compose.data.repositoryImpl.AuthRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.CartRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.CategoryRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.ProductsRepositoryImpl
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.domain.repository.CartRepository
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.presentation.screens.cart.CartViewModel
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesViewModel
import com.example.e_commerce_compose.presentation.screens.home.HomeViewModel
import com.example.e_commerce_compose.presentation.screens.login.SignInViewModel
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsViewModel
import com.example.e_commerce_compose.presentation.screens.wishlist.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoriesModule = module {
    single<CategoriesRepository> { CategoryRepositoryImpl (get()) }
    single<ProductsRepository> { ProductsRepositoryImpl(get() , get() , get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<CartRepository> { CartRepositoryImpl( get() , get() ) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { HomeViewModel(get() , get() , get()) }
    viewModel { ProductDetailsViewModel(get() , get() , get()) }
    viewModel{SignInViewModel(get() , get())}
    viewModel { WishlistViewModel(get()) }
    viewModel { CartViewModel(get()) }
}