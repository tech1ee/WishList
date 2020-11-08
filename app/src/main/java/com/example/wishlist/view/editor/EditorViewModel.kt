package com.example.wishlist.view.editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlist.data.Repository
import com.example.wishlist.entity.Wish
import kotlinx.coroutines.launch

class EditorViewModel(
    private val repository: Repository
): ViewModel() {

    var titleLiveData = MutableLiveData<String>()
    var priceLiveData = MutableLiveData<String>()
    var eventLiveData = MutableLiveData<EditorEvent>()
    private var position = 0

    fun start(wishId: Int?) {
        if (wishId != null && wishId > 0) viewModelScope.launch {
            val wish = repository.getWish(wishId)
            titleLiveData.value = wish.title
            priceLiveData.value = wish.price.toString()
            position = wish.position
        } else initPosition()
    }

    fun checkFields(): Boolean {
        val wish = Wish(
            title = titleLiveData.value ?: "",
            price = priceLiveData.value?.toInt() ?: 0,
            position = this.position
        )
        return when {
            !wish.isTitleValid() -> {
                event(EditorEvent.Error.EmptyTitle)
                false
            }
            !wish.isPriceValid() -> {
                event(EditorEvent.Error.EmptyTitle)
                false
            }
            else -> {
                saveWish(wish)
                true
            }
        }
    }

    private fun initPosition() {
        viewModelScope.launch {
            position = repository.getWishesCount()
        }
    }

    private fun saveWish(wish: Wish) {
        viewModelScope.launch {
            repository.saveWish(wish)
        }
    }

    private fun event(event: EditorEvent) {
        eventLiveData.value = event
    }
}