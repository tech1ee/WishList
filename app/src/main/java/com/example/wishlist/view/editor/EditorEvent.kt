package com.example.wishlist.view.editor

sealed class EditorEvent {
    sealed class Error: EditorEvent() {
        object EmptyTitle: Error()
        object EmptyPrice: Error()
    }
}