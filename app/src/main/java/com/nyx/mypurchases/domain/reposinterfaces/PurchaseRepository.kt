package com.nyx.mypurchases.domain.reposinterfaces

import androidx.lifecycle.LiveData
import com.nyx.mypurchases.domain.entity.PurchaseModel

interface PurchaseRepository {
    fun savePurchase(purchase: PurchaseModel): Long
    fun updatePurchase(purchase: PurchaseModel)
    fun deletePurchase(purchaseId: Long)
    fun getAllPurchases(): List<PurchaseModel>
    fun getPurchaseInfo(purchaseId: Long): LiveData<PurchaseModel>
}