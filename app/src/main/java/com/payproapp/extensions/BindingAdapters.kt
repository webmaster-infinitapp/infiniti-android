package com.payproapp.extensions

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.payproapp.customviews.IndicatorDots


@BindingAdapter("showKeyboard")
fun showKeyboard(view: View, editText: EditText) {
    view.setOnClickListener {
        editText.requestFocus()
        val inputMethodManager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}

@BindingAdapter("updateDotBinding")
fun IndicatorDots.updateView(value: String?) {
    value?.let {
        this.updateDot(it.length)
    }
}

@BindingAdapter("onDoneSoftKeyboard")
fun EditText.onDoneClick(funBind: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            funBind()
            true
        } else {
            false
        }
    }
}

@BindingAdapter("imageBitmap")
fun loadImage(iv: ImageView, bitmap: Bitmap?) {
    bitmap?.let {
        iv.setImageBitmap(it)
    }
}

@BindingAdapter("visibleGone")
fun View.visibleGone(visible: Boolean) {
    this.visibility = when (visible) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}
