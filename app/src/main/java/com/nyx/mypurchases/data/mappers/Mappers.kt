package com.nyx.mypurchases.data.mappers

import com.nyx.mypurchases.data.entities.CategoryRoomEntity
import com.nyx.mypurchases.data.entities.ProductRoomEntity
import com.nyx.mypurchases.data.entities.PurchaseRoomEntity
import com.nyx.mypurchases.domain.entity.CategoryModel
import com.nyx.mypurchases.domain.entity.ProductModel
import com.nyx.mypurchases.domain.entity.PurchaseModel

fun PurchaseModel.toEntity(): PurchaseRoomEntity =
    PurchaseRoomEntity(id, title, category.id)

fun PurchaseRoomEntity.toModel(
    category: CategoryModel,
    products: List<ProductModel>,
): PurchaseModel =
    PurchaseModel(id, title, category, products)

fun CategoryModel.toEntity(): CategoryRoomEntity = CategoryRoomEntity(id, title, isCustom)
fun CategoryRoomEntity.toModel(): CategoryModel =
    CategoryModel(id, title, isCustom)

fun ProductModel.toEntity(purchaseId: Int): ProductRoomEntity =
    ProductRoomEntity(id, purchaseId, title)

fun ProductRoomEntity.toProductModel(purchaseId: Int): ProductModel =
    ProductModel(id, purchaseId, title)