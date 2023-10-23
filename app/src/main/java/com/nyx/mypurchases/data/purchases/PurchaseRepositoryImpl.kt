package com.nyx.mypurchases.data.purchases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.nyx.mypurchases.data.categories.CategoryDao
import com.nyx.mypurchases.data.mappers.toEntity
import com.nyx.mypurchases.data.mappers.toModel
import com.nyx.mypurchases.data.mappers.toProductModel
import com.nyx.mypurchases.data.products.ProductDao
import com.nyx.mypurchases.domain.entity.ProductModel
import com.nyx.mypurchases.domain.entity.PurchaseModel
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    private val purchaseDao: PurchaseDao,
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao,
) : PurchaseRepository {

    override fun savePurchase(purchase: PurchaseModel, products: List<ProductModel>): Long {
        val purchaseId = purchaseDao.savePurchase(purchase.toEntity())
        productDao.insertProducts(products.map { it.toEntity(purchaseId.toInt()) })
        return purchaseId
    }

    override fun updatePurchase(purchase: PurchaseModel) {
        purchaseDao.updatePurchase(purchase.toEntity())
    }

    override fun updateProduct(purchaseId: Int, product: ProductModel) {
        productDao.updateProduct(product.toEntity(purchaseId))
    }

    override fun deletePurchase(purchaseId: Long) {
        purchaseDao.deletePurchase(purchaseId)
    }

    override fun getAllPurchases(): List<PurchaseModel> {
        return purchaseDao.getAllPurchases().map { purchase ->
            val categoryModel = categoryDao.getCategory(purchase.categoryId).toModel()
            val products =
                productDao.getPurchaseProductsSync(purchase.id)
                    .map { it.toProductModel(purchase.id) }

            purchase.toModel(
                categoryModel,
                products
            )
        }
    }

    override fun getPurchaseInfo(purchaseId: Long): LiveData<PurchaseModel> {
        return MediatorLiveData<PurchaseModel>().apply {
            addSource(purchaseDao.getPurchaseInfo(purchaseId)) { purchase ->
                addSource(categoryDao.getCategoryLiveData(purchase.categoryId)) { category ->
                    addSource(productDao.getPurchaseProducts(purchase.id)) { products ->
                        value = purchase.toModel(
                            category.toModel(),
                            products.map { it.toProductModel(purchase.id) }
                        )
                    }
                }
            }
        }
    }

    override fun deleteProducts(productsIds: List<Int>) {
        productDao.deleteProducts(productsIds)
    }
}
