package com.example.e_commerce_compose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_commerce_compose.data.local.cart.CartDao
import com.example.e_commerce_compose.data.local.cart.CartEntity
import com.example.e_commerce_compose.data.local.wishlist.WishlistDao
import com.example.e_commerce_compose.data.local.wishlist.WishlistEntity

@Database(entities = [WishlistEntity::class , CartEntity::class] , version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun wishlistDao() : WishlistDao
    abstract fun cartDao() : CartDao

}