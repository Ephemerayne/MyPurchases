package com.nyx.mypurchases.common

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nyx.mypurchases.R

class CustomSnackbar {

    fun createActionSnackbar(
        view: View,
        duration: Int = 5000,
        actionText: String? = null,
        actionColor: Int = R.color.white,
        snackbarBackgroundColor: Int = android.R.color.darker_gray,
        textColor: Int = android.R.color.black,
        textSize: Float = 14f,
        action: () -> Unit,
    ) {
        val snackbar = Snackbar.make(view, R.string.delete_item_snackbar_title, duration)
            .setAction(actionText.orEmpty()) { action() }
            .setActionTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(view.context, actionColor)
                )
            )

        val snackbarView = snackbar.view

        snackbarView.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(view.context, snackbarBackgroundColor)
        )
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView

        textView.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(view.context, textColor)
            )
        )
        textView.textSize = textSize
        snackbar.show()
    }
}