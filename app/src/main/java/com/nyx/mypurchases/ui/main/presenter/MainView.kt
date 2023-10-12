package com.nyx.mypurchases.ui.main.presenter

import com.nyx.mypurchases.domain.entity.PurchaseModel

interface MainView {
    fun setupPurchasesList(purchases: List<PurchaseModel>)
}