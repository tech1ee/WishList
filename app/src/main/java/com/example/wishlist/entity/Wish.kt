package com.example.wishlist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wish(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var title: String,
    var price: Int,
    var position: Int
) {

    fun isTitleValid() = title.isNotEmpty()

    fun isPriceValid() = price > 0
}