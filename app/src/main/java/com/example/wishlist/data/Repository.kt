package com.example.wishlist.data

import com.example.wishlist.entity.Wish

interface Repository {

    suspend fun getWishesCount(): Int

    suspend fun getAllWishes(): List<Wish>

    suspend fun setWishPosition(wishId: Int, position: Int)

    suspend fun getWish(wishId: Int): Wish

    suspend fun saveWish(wish: Wish)

    suspend fun deleteWish(wishId: Int)
}