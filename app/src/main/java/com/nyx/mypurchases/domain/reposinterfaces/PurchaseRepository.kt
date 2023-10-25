package com.nyx.mypurchases.domain.reposinterfaces

import androidx.lifecycle.LiveData
import com.nyx.mypurchases.domain.entity.ProductModel
import com.nyx.mypurchases.domain.entity.PurchaseModel

interface PurchaseRepository {
    fun savePurchase(purchase: PurchaseModel, products: List<ProductModel>): Long
    fun updatePurchase(purchase: PurchaseModel)
    fun updateProduct(purchaseId: Int, product: ProductModel)
    fun deletePurchase(purchaseId: Long)
    fun getAllPurchases(): List<PurchaseModel>
    fun getPurchaseInfo(purchaseId: Long): LiveData<PurchaseModel?>
    fun deleteProducts(productsIds: List<Int>)
    fun deleteProduct(productsId: Int)
}