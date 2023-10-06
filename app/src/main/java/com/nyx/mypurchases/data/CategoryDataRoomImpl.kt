package com.nyx.mypurchases.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel

@Dao
interface CategoryDataRoomImpl : CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertCategory(category: CategoryChipModel): Long

    @Update
    override fun updateCategory(category: CategoryChipModel)

    @Delete
    override fun deleteCategory(category: CategoryChipModel)

    @Query("DELETE FROM category_table")
    override fun deleteAllCustomCategories()

    @Query("SELECT * FROM category_table")
    override fun getAllCategories(): List<CategoryChipModel>
}