package com.nyx.mypurchases.data.categories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nyx.mypurchases.data.entities.CategoryRoomEntity

@Dao
interface CategoryDataRoomImpl : CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertCategory(category: CategoryRoomEntity): Long

    @Update
    override fun updateCategory(category: CategoryRoomEntity)

    @Delete
    override fun deleteCategory(category: CategoryRoomEntity)

    @Query("DELETE FROM category_table")
    override fun deleteAllCustomCategories()

    @Query("SELECT * FROM category_table")
    override fun getAllCategories(): List<CategoryRoomEntity>

    @Query("SELECT * FROM category_table WHERE id=:id")
    override fun getCategory(id: Int): CategoryRoomEntity
}