package com.example.e_commerce_compose.di

import com.example.e_commerce_compose.data.repositoryImpl.AddressRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.AuthRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.CartRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.CategoryRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.OrdersRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.ProductsRepositoryImpl
import com.example.e_commerce_compose.data.repositoryImpl.WishListRepositoryImpl
import com.example.e_commerce_compose.domain.repository.AddressRepository
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.domain.repository.CartRepository
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import com.example.e_commerce_compose.domain.repository.OrdersRepository
import com.example.e_commerce_compose.domain.repository.ProductsRepository
import com.example.e_commerce_compose.domain.repository.WishListRepository
import com.example.e_commerce_compose.presentation.screens.browse_products.BrowseProductsViewModel
import com.example.e_commerce_compose.presentation.screens.cart.CartViewModel
import com.example.e_commerce_compose.presentation.screens.categories.CategoriesViewModel
import com.example.e_commerce_compose.presentation.screens.checkout.CheckoutViewModel
import com.example.e_commerce_compose.presentation.screens.home.HomeViewModel
import com.example.e_commerce_compose.presentation.screens.login.SignInViewModel
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsViewModel
import com.example.e_commerce_compose.presentation.screens.profile.ProfileViewModel
import com.example.e_commerce_compose.presentation.screens.profile.update_info.UpdateInfoViewModel
import com.example.e_commerce_compose.presentation.screens.signup.SignupViewmodel
import com.example.e_commerce_compose.presentation.screens.wishlist.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoriesModule = module {
    single<CategoriesRepository> { CategoryRepositoryImpl (get()) }
    single<ProductsRepository> { ProductsRepositoryImpl(get() , get() , get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<CartRepository> { CartRepositoryImpl( get() , get() ) }
    single<AddressRepository> { AddressRepositoryImpl(get()) }
    single<OrdersRepository> { OrdersRepositoryImpl(get() , get()) }
    single<WishListRepository> { WishListRepositoryImpl(get() , get()) }

    viewModel { CategoriesViewModel(get()) }
    viewModel { HomeViewModel(get() , get() , get()) }
    viewModel { ProductDetailsViewModel(get() , get() , get() , get() ) }
    viewModel{SignInViewModel(get() , get())}
    viewModel { WishlistViewModel(get() , get()) }
    viewModel { CartViewModel(get()) }
    viewModel { ProfileViewModel(get() , get() , get()) }
    viewModel { CheckoutViewModel(get() , get() , get() ) }
    viewModel { BrowseProductsViewModel(get() , get()) }
    viewModel { SignupViewmodel(get() , get()) }
    viewModel { UpdateInfoViewModel() }

}