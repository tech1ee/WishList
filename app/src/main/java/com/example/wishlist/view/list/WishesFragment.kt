package com.example.wishlist.view.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wishlist.R
import com.example.wishlist.databinding.FragmentWishesBinding
import com.example.wishlist.entity.Wish
import com.example.wishlist.view.MessageDialogFragment
import com.example.wishlist.view.editor.EditorDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class WishesFragment : Fragment() {

    private val viewModel by viewModel<WishesViewModel>()
    private val adapter = WishesAdapter({ id -> openWishEditor(id) },
        { wishes -> viewModel.onItemMoved(wishes) }, { wish -> showDeleteMessage(wish) })
    private val touchCallback = WishTouchHelper(adapter)
    private val touchHelper = ItemTouchHelper(touchCallback)
    private lateinit var binding: FragmentWishesBinding
    private val editorListener = object : EditorDialogFragment.Listener {
        override fun onEditorClosed() {
            viewModel.getAllWishes()
            context?.hideKeyboard()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishesBinding.inflate(inflater)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        context?.hideKeyboard()
        viewModel.getAllWishes()
    }

    fun newWish() {
        openWishEditor()
    }

    private fun openWishEditor(wishId: Int? = null) {
        val editor = EditorDialogFragment.newInstance(wishId)
        editor.show(childFragmentManager, editor.tag)
        editor.setListener(editorListener)
    }

    private fun initView() {
        binding.wishesRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.wishesRv.adapter = adapter
        touchHelper.attachToRecyclerView(binding.wishesRv)
    }

    private fun observeViewModel() {
        viewModel.eventLiveData.observe(viewLifecycleOwner, { event ->
            when (event) {
                is WishesEvent.Wishes -> adapter.setWishes(event.list)
            }
        })
    }

    private fun showDeleteMessage(wish: Wish) {
        val messageListener = object : MessageDialogFragment.Listener {
            override fun onCancelClicked() {
                viewModel.getAllWishes()
            }

            override fun onOkClicked() {
                viewModel.onItemDeleted(wish)
            }
        }
        val dialog = MessageDialogFragment.newInstance(
            getString(R.string.delete_wish_question),
            getString(R.string.delete)
        )
        dialog.show(childFragmentManager, dialog.tag)
        dialog.setListener(messageListener)
    }

    private fun Context.hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}