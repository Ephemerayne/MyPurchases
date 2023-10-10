package com.nyx.mypurchases.ui.main.presenter

import androidx.lifecycle.LifecycleCoroutineScope
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import javax.inject.Inject

class MainPresenter @Inject constructor(
    val purchaseRepository: PurchaseRepository,
) {

    private lateinit var view: MainView
    private lateinit var lifecycleCoroutineScope: LifecycleCoroutineScope

    fun attachView(
        view: MainView,
        lifecycleCoroutineScope: LifecycleCoroutineScope,
    ) {
        this.view = view
        this.lifecycleCoroutineScope = lifecycleCoroutineScope

    //    purchaseRepository.getAllPurchases()
    }
}