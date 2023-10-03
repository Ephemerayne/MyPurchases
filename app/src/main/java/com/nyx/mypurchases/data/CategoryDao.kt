package com.nyx.mypurchases.data

import androidx.lifecycle.LiveData
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel

interface CategoryDao {

    fun insertCategory(category: CategoryChipModel)

    fun updateCategory(category: CategoryChipModel)

    fun deleteCategory(category: CategoryChipModel)

    fun deleteAllCustomCategories()

    fun getAllCategories(): LiveData<List<CategoryChipModel>>
}