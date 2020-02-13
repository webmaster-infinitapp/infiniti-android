package com.payproapp.extensions

import android.content.Context
import android.widget.TextView
import androidx.annotation.ColorInt
import com.google.android.material.snackbar.Snackbar
import com.payproapp.R

fun Context.getDimensionInPx(resDimId: Int): Float {
    return this.resources.getDimension(resDimId)
}

fun Snackbar.withTextColor(@ColorInt textColor: Int): Snackbar {
    this.view.findViewById<TextView>(R.id.snackbar_text)
            .setTextColor(textColor)
    return this
}