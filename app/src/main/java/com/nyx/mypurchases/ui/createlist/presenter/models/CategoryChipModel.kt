package com.nyx.mypurchases.ui.createlist.presenter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryChipModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val isCustom: Boolean = false,
)

data class CategoriesModel(
    val categories: MutableList<CategoryChipModel>,
    val lastAddedCategoryId: Long?
)

class ChipsList {
    private val _items = mutableListOf<CategoryChipModel>(
        CategoryChipModel(0, "Смешанная"),
        CategoryChipModel(1, "Продукты"),
        CategoryChipModel(2, "Косметика"),
        CategoryChipModel(3, "Аптека"),
        CategoryChipModel(4, "Для дома"),
        CategoryChipModel(5, "Ремонт"),
        CategoryChipModel(-1 ,"Новая"),
    )

    val items = _items
    private val customItems = mutableListOf<CategoryChipModel>()

    fun addChip(chipModel: CategoryChipModel) {
        val chip = CategoryChipModel(chipModel.id, chipModel.title, true)
        val lastDefaultIndex = _items.filter { !it.isCustom }.lastIndex

        customItems.add(chip)

        if (customItems.isEmpty()) {
            _items.add(lastDefaultIndex, chip)
        } else {
            val lastCustomIndex = lastDefaultIndex + customItems.lastIndex
            _items.add(lastCustomIndex, chip)
        }
    }

    fun removeChip(chipModel: CategoryChipModel) {
        customItems.remove(chipModel)
    }
}

