package com.example.e_commerce_compose.data.local.wishlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class WishlistEntity(
    @PrimaryKey
    val id: String,
)
