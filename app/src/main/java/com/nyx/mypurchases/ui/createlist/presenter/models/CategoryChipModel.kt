package com.nyx.mypurchases.ui.createlist.presenter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryChipModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val isCustom: Boolean = false,
)
