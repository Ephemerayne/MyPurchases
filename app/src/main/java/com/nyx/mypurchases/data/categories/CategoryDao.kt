package com.nyx.mypurchases.data.categories

import androidx.lifecycle.LiveData
import com.nyx.mypurchases.data.entities.CategoryRoomEntity

interface CategoryDao {

    fun insertCategory(category: CategoryRoomEntity): Long

    fun updateCategory(category: CategoryRoomEntity)

    fun deleteCategory(category: CategoryRoomEntity)

    fun deleteAllCustomCategories()

    fun getAllCategories(): List<CategoryRoomEntity>

    fun getAllCategoriesLiveData(): LiveData<List<CategoryRoomEntity>>

    fun getCategory(id: Int): CategoryRoomEntity

    fun getCategoryLiveData(id: Int): LiveData<CategoryRoomEntity>
}