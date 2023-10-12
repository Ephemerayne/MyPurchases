package com.nyx.mypurchases.data.mappers

import com.nyx.mypurchases.data.entities.CategoryRoomEntity
import com.nyx.mypurchases.data.entities.PurchaseRoomEntity
import com.nyx.mypurchases.domain.entity.CategoryModel
import com.nyx.mypurchases.domain.entity.PurchaseModel

fun PurchaseModel.toEntity(): PurchaseRoomEntity =
    PurchaseRoomEntity(id, title, purchases, category.id)

fun PurchaseRoomEntity.toModel(category: CategoryModel): PurchaseModel =
    PurchaseModel(id, title, purchases, category)

fun CategoryModel.toEntity(): CategoryRoomEntity = CategoryRoomEntity(id, title, isCustom)
fun CategoryRoomEntity.toModel(): CategoryModel =
    CategoryModel(id, title, isCustom)