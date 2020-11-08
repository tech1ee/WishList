package com.example.wishlist.data

import com.example.wishlist.entity.Wish

class RepositoryImpl(
    private val dao: Dao
): Repository {

    override suspend fun getWishesCount(): Int {
        return dao.getAllWishes().size
    }

    override suspend fun getAllWishes(): List<Wish> {
        return dao.getAllWishes()
    }

    override suspend fun setWishPosition(wishId: Int, position: Int) {
        dao.setWishPosition(wishId, position)
    }

    override suspend fun getWish(wishId: Int): Wish {
        return dao.getWishById(wishId)
    }

    override suspend fun saveWish(wish: Wish) {
        dao.saveWish(wish)
    }

    override suspend fun deleteWish(wishId: Int) {
        dao.deleteWish(wishId)
    }
}