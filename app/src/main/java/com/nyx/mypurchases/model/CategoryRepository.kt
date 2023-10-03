package com.nyx.mypurchases.model

import androidx.lifecycle.LiveData
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel

interface CategoryRepository {
    fun insertCategory(category: CategoryChipModel)

    fun updateCategory(category: CategoryChipModel)

    fun deleteCategory(category: CategoryChipModel)
    fun deleteAllCustomCategories()

    fun getAllCategories(): LiveData<List<CategoryChipModel>>
}