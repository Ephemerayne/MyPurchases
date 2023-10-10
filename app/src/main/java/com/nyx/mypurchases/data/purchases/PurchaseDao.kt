package com.nyx.mypurchases.data.purchases

import com.nyx.mypurchases.data.entities.PurchaseModelDatabaseEntity

interface PurchaseDao {
    fun savePurchase(purchase: PurchaseModelDatabaseEntity): Long
    fun updatePurchase(purchase: PurchaseModelDatabaseEntity)
    fun deletePurchase(purchase: PurchaseModelDatabaseEntity)
    fun getAllPurchases(): List<PurchaseModelDatabaseEntity>
}