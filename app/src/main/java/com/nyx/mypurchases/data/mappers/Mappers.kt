package com.nyx.mypurchases.data.mappers

import com.nyx.mypurchases.data.entities.PurchaseModelDatabaseEntity
import com.nyx.mypurchases.domain.entity.CategoryModel
import com.nyx.mypurchases.domain.entity.PurchaseModel

fun PurchaseModel.toEntity(): PurchaseModelDatabaseEntity =
    PurchaseModelDatabaseEntity(id, title, purchases, category.id)

fun PurchaseModelDatabaseEntity.toPurchaseModel(category: CategoryModel): PurchaseModel =
    PurchaseModel(id, title, purchases, category)