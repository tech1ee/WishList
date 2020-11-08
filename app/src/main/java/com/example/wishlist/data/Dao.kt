package com.example.wishlist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wishlist.entity.Wish

@Dao
interface Dao {

    @Query("SELECT * FROM wish")
    suspend fun getAllWishes(): List<Wish>

    @Query("SELECT * FROM wish WHERE id IS :id")
    suspend fun getWishById(id: Int): Wish

    @Query("UPDATE wish SET position = :position WHERE id = :id")
    suspend fun setWishPosition(id: Int, position: Int)

    @Query("DELETE FROM wish WHERE id IS :id")
    suspend fun deleteWish(id: Int)

    @Insert
    suspend fun saveWish(wish: Wish)
}