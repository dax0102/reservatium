package me.asayah.reservatium.components.extensions

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun TextView.setTextColorRes(@ColorRes id: Int) {
    this.setTextColor(ContextCompat.getColor(context, id))
}