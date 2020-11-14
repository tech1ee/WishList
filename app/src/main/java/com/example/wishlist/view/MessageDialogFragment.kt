package com.example.wishlist.view

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.wishlist.databinding.ViewMessageBinding

class MessageDialogFragment: DialogFragment() {

    private lateinit var binding: ViewMessageBinding
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewMessageBinding.inflate(inflater)
        binding.dialog = this
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
    }

    fun onCancelClicked() {
        listener?.onCancelClicked()
        dismiss()
    }

    fun onOkClicked() {
        listener?.onOkClicked()
        dismiss()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onCancelClicked()
        fun onOkClicked()
    }

    companion object {

        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        const val EXTRA_OK_BTN_TEXT = "EXTRA_OK_BTN_TEXT"

        fun newInstance(message: String, okButtonText: String): MessageDialogFragment {
            return MessageDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MESSAGE, message)
                    putString(EXTRA_OK_BTN_TEXT, okButtonText)
                }
            }
        }
    }
}