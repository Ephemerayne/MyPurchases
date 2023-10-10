package com.nyx.mypurchases.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_table")
data class PurchaseModelDatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val purchases: List<String>? = null,
    val categoryId: Int = 0,
)