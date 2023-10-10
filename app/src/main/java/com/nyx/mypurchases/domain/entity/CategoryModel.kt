package com.nyx.mypurchases.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val isCustom: Boolean = false,
)

