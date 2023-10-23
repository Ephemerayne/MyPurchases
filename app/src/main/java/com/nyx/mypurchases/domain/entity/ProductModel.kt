package com.nyx.mypurchases.domain.entity

data class ProductModel(
    val id: Int = 0,
    val purchaseId: Int = 0,
    val title: String,
    val isChecked: Boolean = false
)