package com.nyx.mypurchases.data.categories

import com.nyx.mypurchases.domain.entity.CategoryModel

interface CategoryDao {

    fun insertCategory(category: CategoryModel): Long

    fun updateCategory(category: CategoryModel)

    fun deleteCategory(category: CategoryModel)

    fun deleteAllCustomCategories()

    fun getAllCategories(): List<CategoryModel>

    fun getCategory(id: Int): CategoryModel
}