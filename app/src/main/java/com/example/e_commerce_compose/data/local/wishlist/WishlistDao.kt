package com.example.e_commerce_compose.data.local.wishlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WishlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wishlist: WishlistEntity)

    //get all ids
    @Query("SELECT id FROM wishlist")
    suspend fun getAllIds(): List<String>

    @Query("DELETE FROM wishlist WHERE id = :id")
    suspend fun delete(id: String)


}