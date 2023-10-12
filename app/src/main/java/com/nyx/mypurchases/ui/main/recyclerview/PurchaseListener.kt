package com.nyx.mypurchases.ui.main.recyclerview

import com.nyx.mypurchases.data.entities.PurchaseRoomEntity

public interface PurchaseListener {

    fun onPurchaseClick(purchase: PurchaseRoomEntity)
}
