package com.nyx.mypurchases.data.purchases

import com.nyx.mypurchases.data.categories.CategoryDao
import com.nyx.mypurchases.data.mappers.toEntity
import com.nyx.mypurchases.data.mappers.toModel
import com.nyx.mypurchases.domain.entity.PurchaseModel
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    private val purchaseDao: PurchaseDao,
    private val categoryDao: CategoryDao,
) : PurchaseRepository {

    override fun savePurchase(purchase: PurchaseModel): Long {
        return purchaseDao.savePurchase(purchase.toEntity())
    }

    override fun updatePurchase(purchase: PurchaseModel) {
        purchaseDao.updatePurchase(purchase.toEntity())
    }

    override fun deletePurchase(purchase: PurchaseModel) {
        purchaseDao.deletePurchase(purchase.toEntity())
    }

    override fun getAllPurchases(): List<PurchaseModel> {
        return purchaseDao.getAllPurchases().map {
            it.toModel(categoryDao.getCategory(it.categoryId).toModel())
        }
    }
}