package com.nyx.mypurchases.extensions

import android.content.res.ColorStateList
import android.content.res.Resources
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat

fun ImageView.setTint(@ColorRes color: Int?) {
    ImageViewCompat.setImageTintList(
        this,
        if (color == null) null else ColorStateList.valueOf(ContextCompat.getColor(context, color))
    )
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()