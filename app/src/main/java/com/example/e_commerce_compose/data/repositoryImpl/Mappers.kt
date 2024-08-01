package com.example.e_commerce_compose.data.repositoryImpl

import com.example.e_commerce_compose.data.model.categories.CategoryDto
import com.example.e_commerce_compose.domain.model.Category

fun CategoryDto.toDomainModel(): Category {
    return Category(
        id = id,
        name = name,
        image = image,
        createdAt = createdAt,
        updatedAt = updatedAt,
        slug = slug
    )
}