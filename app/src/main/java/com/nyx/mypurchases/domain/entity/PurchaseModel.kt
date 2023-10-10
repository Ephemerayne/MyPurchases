package com.nyx.mypurchases.domain.entity

data class PurchaseModel(
    val id: Int = 0,
    val title: String = "",
    val purchases: List<String>? = null,
    val category: CategoryModel = CategoryModel(),
)