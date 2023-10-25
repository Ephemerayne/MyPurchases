package com.nyx.mypurchases.common

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.nyx.mypurchases.R

class AddProductDialog(val context: Context) {

    private var dialog: Dialog = Dialog(context)

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_product_dialog)
    }

    fun show(onAddButtonClick: (text: String) -> Unit) {
        val textField = dialog.findViewById(R.id.add_products_edit_text) as EditText

        val button = dialog.findViewById(R.id.dialog_button) as Button

        button.setOnClickListener {
            onAddButtonClick(textField.text.toString())
            dialog.dismiss()
        }

        textField.addTextChangedListener {
            button.isEnabled = textField.text.toString().isNotBlank()
        }

        dialog.show()

        val window: Window? = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}