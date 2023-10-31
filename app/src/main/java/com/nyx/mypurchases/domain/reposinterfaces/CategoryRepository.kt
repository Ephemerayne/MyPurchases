package com.nyx.mypurchases.domain.reposinterfaces

import androidx.lifecycle.LiveData
import com.nyx.mypurchases.domain.entity.CategoryModel

interface CategoryRepository {
    fun insertCategory(category: CategoryModel): Long

    fun updateCategory(category: CategoryModel)

    fun deleteCategory(category: CategoryModel)
    fun deleteAllCustomCategories()

    fun getAllCategories(): List<CategoryModel>
    fun getAllCategoriesLiveData(): LiveData<List<CategoryModel>>
}