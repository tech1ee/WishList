package com.example.wishlist.view.list

import com.example.wishlist.entity.Wish

sealed class WishesEvent {
    data class Wishes(val list: List<Wish>): WishesEvent()
}