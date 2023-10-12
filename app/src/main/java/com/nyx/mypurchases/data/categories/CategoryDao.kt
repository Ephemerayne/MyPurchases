package com.nyx.mypurchases.data.categories

import com.nyx.mypurchases.data.entities.CategoryRoomEntity

interface CategoryDao {

    fun insertCategory(category: CategoryRoomEntity): Long

    fun updateCategory(category: CategoryRoomEntity)

    fun deleteCategory(category: CategoryRoomEntity)

    fun deleteAllCustomCategories()

    fun getAllCategories(): List<CategoryRoomEntity>

    fun getCategory(id: Int): CategoryRoomEntity
}