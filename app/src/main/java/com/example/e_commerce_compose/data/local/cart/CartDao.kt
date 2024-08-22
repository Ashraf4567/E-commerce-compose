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
    @Query("SELECT id FROM wishlist")
    suspend fun getAllIds(): List<String>

    //delete by id
    @Query("DELETE FROM wishlist WHERE id = :id")
    suspend fun deleteById(id: String)


}