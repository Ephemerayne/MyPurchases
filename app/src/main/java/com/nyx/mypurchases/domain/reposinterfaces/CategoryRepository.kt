package com.nyx.mypurchases.domain.reposinterfaces

import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel

interface CategoryRepository {
    fun insertCategory(category: CategoryChipModel): Long

    fun updateCategory(category: CategoryChipModel)

    fun deleteCategory(category: CategoryChipModel)
    fun deleteAllCustomCategories()

    fun getAllCategories(): List<CategoryChipModel>
}