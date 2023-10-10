package com.nyx.mypurchases.domain.reposinterfaces

import com.nyx.mypurchases.domain.entity.PurchaseModel

interface PurchaseRepository {
    fun savePurchase(purchase: PurchaseModel): Long
    fun updatePurchase(purchase: PurchaseModel)
    fun deletePurchase(purchase: PurchaseModel)
    fun getAllPurchases(): List<PurchaseModel>
}