package com.example.wishlist.view.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlist.data.Repository
import com.example.wishlist.entity.Wish
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class WishesViewModel(
    private val repository: Repository
) : ViewModel() {

    val eventLiveData = MutableLiveData<WishesEvent>()
    val totalPriceLiveData = MutableLiveData<String>()
    private var wishes = arrayListOf<Wish>()

    fun getAllWishes() {
        viewModelScope.launch {
            wishes = repository.getAllWishes() as ArrayList<Wish>
            wishes.sortBy { it.position }
            event(WishesEvent.Wishes(wishes))
            calculateTotalPrice()
        }
    }

    fun onItemMoved(wishes: List<Wish>) {
        viewModelScope.launch {
           wishes.forEachIndexed { index, wish ->
               if (wish.id == null) return@forEachIndexed
               repository.setWishPosition(wish.id, index)
           }
        }
    }

    fun onItemDeleted(wish: Wish) {
        viewModelScope.launch {
            if (wish.id == null) return@launch
            repository.deleteWish(wish.id)
        }
    }

    private fun calculateTotalPrice() {
        var total = 0
        wishes.forEach { total += it.price }
        totalPriceLiveData.value = total.toString()
    }

    private fun event(event: WishesEvent) {
        eventLiveData.value = event
    }
}