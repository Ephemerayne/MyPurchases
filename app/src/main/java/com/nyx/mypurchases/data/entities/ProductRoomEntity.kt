package com.nyx.mypurchases.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
)