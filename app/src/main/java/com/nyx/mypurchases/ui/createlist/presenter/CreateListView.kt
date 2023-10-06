package com.nyx.mypurchases.ui.createlist.presenter

import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel

interface CreateListView {
    fun toggleCustomCategoryFieldVisibility(isVisible: Boolean)
    fun toggleEditCategoriesMode(isEnabled: Boolean)
    fun setDeleteCategoryVisibility(isVisible: Boolean)
    fun setupChips(categories: List<CategoryChipModel>)
}