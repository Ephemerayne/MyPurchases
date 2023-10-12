package com.nyx.mypurchases.ui.main.recyclerview

import com.nyx.mypurchases.data.entities.PurchaseModelDatabaseEntity

public interface PurchaseListener {

    fun onPurchaseClick(purchase: PurchaseModelDatabaseEntity)
}
