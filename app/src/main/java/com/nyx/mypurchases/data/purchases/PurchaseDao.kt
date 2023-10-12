package com.nyx.mypurchases.data.purchases

import com.nyx.mypurchases.data.entities.PurchaseRoomEntity

interface PurchaseDao {
    fun savePurchase(purchase: PurchaseRoomEntity): Long
    fun updatePurchase(purchase: PurchaseRoomEntity)
    fun deletePurchase(purchase: PurchaseRoomEntity)
    fun getAllPurchases(): List<PurchaseRoomEntity>
}