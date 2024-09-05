package com.example.e_commerce_compose.data

import com.example.e_commerce_compose.data.model.AddressDto
import com.example.e_commerce_compose.data.model.cart.ProductsItem
import com.example.e_commerce_compose.data.model.categories.CategoryDto
import com.example.e_commerce_compose.data.model.categories.SubCategoryDto
import com.example.e_commerce_compose.data.model.products.BrandDto
import com.example.e_commerce_compose.data.model.products.ProductDto
import com.example.e_commerce_compose.domain.model.Address
import com.example.e_commerce_compose.domain.model.Brand
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.Product
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

fun BrandDto.toDomain(): Brand{
    return Brand(
        image = image,
        name = name,
        id = id,
        slug = slug
    )
}

fun ProductDto.toDomain(): Product {
    return Product(
        sold = sold,
        images = images,
        quantity = quantity,
        availableColors = availableColors,
        imageCover = imageCover,
        description = description,
        title = title,
        ratingsQuantity = ratingsQuantity,
        ratingsAverage = ratingsAverage,
        price = price,
        id = id,
        subcategory = subcategory?.map { it?.toDomain() },
        category = category?.toDomainModel(),
        brand = brand?.toDomain(),
        slug = slug,
        isFavorite = false,
        isInCart = false,
    )
}

fun AddressDto.toDomain(): Address{
    return Address(
        id = id,
        name = name,
        phone = phone,
        city = city,
        details = details
        )
}


























