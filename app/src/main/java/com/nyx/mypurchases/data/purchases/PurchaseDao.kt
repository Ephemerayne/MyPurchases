package com.nyx.mypurchases.data.purchases

import androidx.lifecycle.LiveData
import com.nyx.mypurchases.data.entities.PurchaseRoomEntity

interface PurchaseDao {
    fun savePurchase(purchase: PurchaseRoomEntity): Long
    fun updatePurchase(purchase: PurchaseRoomEntity)
    fun deletePurchase(purchaseId: Long)
    fun getAllPurchases(): List<PurchaseRoomEntity>
    fun getPurchaseInfo(purchaseId: Long): LiveData<PurchaseRoomEntity>
}