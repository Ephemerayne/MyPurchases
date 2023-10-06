package com.nyx.mypurchases.data

import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel

interface CategoryDao {

    fun insertCategory(category: CategoryChipModel): Long

    fun updateCategory(category: CategoryChipModel)

    fun deleteCategory(category: CategoryChipModel)

    fun deleteAllCustomCategories()

    fun getAllCategories(): List<CategoryChipModel>
}