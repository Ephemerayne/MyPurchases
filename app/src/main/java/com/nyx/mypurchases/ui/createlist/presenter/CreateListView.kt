package com.nyx.mypurchases.ui.createlist.presenter

import com.nyx.mypurchases.domain.entity.CategoryModel

interface CreateListView {
    fun toggleCustomCategoryFieldVisibility(isVisible: Boolean)
    fun toggleEditCategoriesMode(isEnabled: Boolean)
    fun setDeleteCategoryVisibility(isAvailable: Boolean)
    fun setupChips(
        categories: List<CategoryModel>, checkedId: Int?,
        isEditModeEnabled: Boolean,
    )
    fun addCategoryButtonClickable(isEnabled: Boolean)
    fun createListButtonClickable(isEnabled: Boolean)
    fun setupTitleFieldView(currentChars: Int, maxChars: Int)
    fun setupCategoryTitleFieldView(currentChars: Int, maxChars: Int)
    fun backToMainScreen()
}