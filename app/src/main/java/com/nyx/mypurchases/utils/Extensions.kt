package com.nyx.mypurchases.utils

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
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

fun dpToFloat(context: Context, dp: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        context.resources.displayMetrics
    )
}

inline fun <reified T : Parcelable?> Bundle.getParcelableCompat(
    key: String,
    clazz: Class<T>,
): T? {
    return if (Build.VERSION.SDK_INT >= 33) {
        this.getParcelable(key,clazz)
    } else {
        this.getParcelable(key)
    }
}