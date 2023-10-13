package com.nyx.mypurchases.ui.main.presenter

import androidx.lifecycle.LifecycleCoroutineScope
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        refreshPurchasesLists()
    }

    private fun refreshPurchasesLists() {
        lifecycleCoroutineScope.launch {
            val purchases = withContext(Dispatchers.IO) {
                purchaseRepository.getAllPurchases()
            }

            view.setupPurchasesList(purchases)
        }
    }

    fun removePurchase(purchaseId: Int) {
        lifecycleCoroutineScope.launch {
            withContext(Dispatchers.IO) {
                purchaseRepository.deletePurchase(purchaseId.toLong())
            }
        }
    }
}