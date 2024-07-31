package com.example.e_commerce_compose.di

import com.example.e_commerce_compose.data.repositoryImpl.CategoryRepositoryImpl
import com.example.e_commerce_compose.domain.repository.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun provideCategoriesRepo(categoriesRepo: CategoryRepositoryImpl): CategoriesRepository
}