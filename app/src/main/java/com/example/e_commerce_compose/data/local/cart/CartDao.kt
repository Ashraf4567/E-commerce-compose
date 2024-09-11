package com.example.e_commerce_compose.data.local.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wishlist: CartEntity)

    //get all ids
    @Query("SELECT id FROM cart_entity")
    suspend fun getAllIds(): List<String>

    //delete by id
    @Query("DELETE FROM cart_entity WHERE id = :id")
    suspend fun deleteById(id: String)

    // clear table
    @Query("DELETE FROM cart_entity")
    suspend fun clearTable()


}