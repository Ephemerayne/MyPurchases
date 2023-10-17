package com.nyx.mypurchases.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_table")
data class PurchaseRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val categoryId: Int
)