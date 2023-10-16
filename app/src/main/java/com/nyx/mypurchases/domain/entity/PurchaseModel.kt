package com.nyx.mypurchases.domain.entity

data class PurchaseModel(
    val id: Int = 0,
    val title: String = "",
    val products: List<ProductModel> = emptyList(),
    val category: CategoryModel = CategoryModel(),
)