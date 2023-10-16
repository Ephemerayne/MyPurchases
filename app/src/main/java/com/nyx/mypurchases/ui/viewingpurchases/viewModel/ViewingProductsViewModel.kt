package com.nyx.mypurchases.ui.viewingpurchases.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nyx.mypurchases.domain.entity.PurchaseModel
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import javax.inject.Inject


class ViewingProductsViewModel @Inject constructor(
    val purchaseRepository: PurchaseRepository,
) : ViewModel() {

    fun getPurchases(purchaseId: Int): LiveData<PurchaseModel> =
        purchaseRepository.getPurchaseInfo(purchaseId.toLong())
}