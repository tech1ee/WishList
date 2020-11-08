package com.example.wishlist.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wishlist.databinding.ItemWishBinding
import com.example.wishlist.entity.Wish
import java.util.*

class WishesAdapter(
    private val onWishClicked: (wishId: Int) -> Unit,
    private val onItemMoved: (wishes: List<Wish>) -> Unit,
    private val onItemDeleted: (wish: Wish) -> Unit
): RecyclerView.Adapter<WishesAdapter.ViewHolder>(), WishTouchHelper.WishTouchHelperAdapter {

    private var wishes = arrayListOf<Wish>()

    fun setWishes(wishes: List<Wish>) {
        this.wishes = wishes as ArrayList<Wish>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWishBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wishes[position])
    }

    override fun getItemCount() = wishes.size

    fun onItemClicked(wishId: Int) {
        onWishClicked(wishId)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) Collections.swap(wishes, i, i + 1)
        } else {
            for (i in fromPosition downTo toPosition + 1) Collections.swap(wishes, i, i - 1)
        }
        onItemMoved(wishes)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        onItemDeleted(wishes[position])
        wishes.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(
        private val binding: ItemWishBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(wish: Wish) {
            binding.wish = wish
            binding.executePendingBindings()
        }
    }
}