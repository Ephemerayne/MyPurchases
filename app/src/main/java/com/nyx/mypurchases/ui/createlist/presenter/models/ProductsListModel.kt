package com.nyx.mypurchases.ui.createlist.presenter.models

data class ProductsListModel(
    val id: Int = 0,
    val title: String = "",
    val products: List<String>? = null,
    val categoryChipModel: CategoryChipModel = CategoryChipModel()
)