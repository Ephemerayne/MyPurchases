package com.nyx.mypurchases.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nyx.mypurchases.domain.entity.ProductModel

@Entity(tableName = "purchase_table")
data class PurchaseRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val categoryId: Int = 0,

    // TODO ti list ints
    val products: List<ProductModel> = emptyList(),
)