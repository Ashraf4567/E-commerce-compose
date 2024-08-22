package com.example.e_commerce_compose.data.local.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_entity")
data class CartEntity(
    @PrimaryKey
    val id: String,
)
