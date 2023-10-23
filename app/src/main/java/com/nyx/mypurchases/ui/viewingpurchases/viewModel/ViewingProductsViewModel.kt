package com.nyx.mypurchases.ui.viewingpurchases.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyx.mypurchases.common.Timer
import com.nyx.mypurchases.domain.entity.ProductModel
import com.nyx.mypurchases.domain.entity.PurchaseModel
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ViewingProductsViewModel @Inject constructor(
    val purchaseRepository: PurchaseRepository,
) : ViewModel() {

    private val deletedProducts: MutableSet<ProductModel> = mutableSetOf()

    fun getPurchaseInfo(purchaseId: Int): LiveData<PurchaseModel> {
        val purchase = purchaseRepository.getPurchaseInfo(purchaseId.toLong())

        return MediatorLiveData<PurchaseModel>().apply {
            addSource(purchase) { purchaseModel ->
                value = purchaseModel?.copy(products = purchaseModel.products - deletedProducts)
            }
        }
    }

    fun removeProduct(product: ProductModel) {
        deletedProducts.add(product)

        val timer = Timer(
            delayMillis = 5000,
            repeatMillis = 0,
            backgroundWork = {
                purchaseRepository.deleteProducts(deletedProducts.map { it.id })
            }
        )

        timer.startTimer()
    }

    fun toggleProductCheck(
        purchaseId: Int,
        product: ProductModel,
        isChecked: Boolean,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            purchaseRepository.updateProduct(purchaseId, product.copy(isChecked = isChecked))
        }
    }

    fun cancelDeletion() {
        deletedProducts.clear()
    }
}