package com.example.e_commerce_compose.data

import com.example.e_commerce_compose.data.model.categories.CategoryDto
import com.example.e_commerce_compose.data.model.categories.SubCategoryDto
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.SubCategory

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

fun SubCategoryDto.toDomain(): SubCategory{
    return SubCategory(
        id = id,
        name = name,
        slug = slug,
        category = category,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}