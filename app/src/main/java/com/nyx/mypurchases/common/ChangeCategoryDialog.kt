package com.nyx.mypurchases.common

import android.app.Dialog
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.nyx.mypurchases.R
import com.nyx.mypurchases.domain.entity.CategoryModel

fun Fragment.showChangeCategoryDialog(
    viewLifecycleOwner: LifecycleOwner,
    categories: LiveData<List<CategoryModel>>,
    checkedCategoryId: Int,
    onChangeButtonClick: (categoryId: Int) -> Unit,
) {
    val dialog = Dialog(requireContext())

    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.change_category_dialog)

    val chipGroup = dialog.findViewById<ChipGroup>(R.id.chip_group)
    val changeButton = dialog.findViewById<Button>(R.id.change_category_button)

    var currentCheckedId = checkedCategoryId

    categories.observe(viewLifecycleOwner) { categoriesLiveData ->
        categoriesLiveData.forEach { category ->
            val chip = Chip(requireContext())
            chip.text = category.title
            chip.id = category.id
            chip.isCheckable = true
            chip.isChecked = category.id == currentCheckedId

            chipGroup.addView(chip)

            chip.setOnClickListener {
                currentCheckedId = category.id
            }
        }

        changeButton.setOnClickListener {
            onChangeButtonClick(currentCheckedId)
            dialog.dismiss()
        }

        dialog.show()

        val window: Window? = dialog.window
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}