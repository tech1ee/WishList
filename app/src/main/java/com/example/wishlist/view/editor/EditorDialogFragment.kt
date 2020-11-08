package com.example.wishlist.view.editor

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.example.wishlist.R
import com.example.wishlist.databinding.ViewEditorBinding
import org.koin.android.viewmodel.ext.android.viewModel


class EditorDialogFragment: DialogFragment() {

    private lateinit var binding: ViewEditorBinding
    private val viewModel by viewModel<EditorViewModel>()
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewEditorBinding.inflate(inflater)
        binding.dialog = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val window: Window? = dialog?.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        val width: Int = size.x
        window?.setLayout((width * 0.85).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        observeViewModel()
        val wishId: Int? = arguments?.getInt(EXTRA_WISH_ID)
        viewModel.start(wishId)
    }

    override fun onResume() {
        super.onResume()
        context?.showKeyboard()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
//        context?.hideKeyboard()
        listener?.onEditorClosed()
    }

    private fun observeViewModel() {
        viewModel.eventLiveData.observe(viewLifecycleOwner, { event ->
            when (event) {
                is EditorEvent.Error.EmptyTitle -> binding.titleTextInput.error =
                    getString(R.string.error_enter_title)
                is EditorEvent.Error.EmptyPrice -> binding.priceTextInput.error =
                    getString(R.string.error_enter_price)
            }
        })
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    private fun Context.showKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    private fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    interface Listener {
        fun onEditorClosed()
    }

    companion object {

        private const val EXTRA_WISH_ID = "EXTRA_WISH_ID"

        fun newInstance(wishId: Int?) = EditorDialogFragment().apply {
            arguments = Bundle().apply {
                if (wishId != null) putInt(EXTRA_WISH_ID, wishId)
            }
        }
    }
}