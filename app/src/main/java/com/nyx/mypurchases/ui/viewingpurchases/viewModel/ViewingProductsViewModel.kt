package com.nyx.mypurchases.ui.viewingpurchases.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyx.mypurchases.domain.entity.CategoryModel
import com.nyx.mypurchases.domain.entity.ProductModel
import com.nyx.mypurchases.domain.entity.PurchaseModel
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ViewingProductsViewModel @Inject constructor(
    val purchaseRepository: PurchaseRepository,
    val categoryRepository: CategoryRepository,
) : ViewModel() {

//    private val deletedProducts: MutableSet<ProductModel> = mutableSetOf()

    fun getPurchaseInfo(
        purchaseId: Int,
    ): LiveData<PurchaseModel> {
        val purchase = purchaseRepository.getPurchaseInfo(purchaseId.toLong())

        return MediatorLiveData<PurchaseModel>().apply {
            addSource(purchase) { purchaseModel ->
//                value = purchaseModel?.copy(products = purchaseModel.products - deletedProducts)
                value = purchaseModel?.copy(products = purchaseModel.products.sortedBy { it.isChecked })
            }
        }
    }

    fun getCategories(): LiveData<List<CategoryModel>> {
        return categoryRepository.getAllCategoriesLiveData()
    }

    fun addProducts(purchase: PurchaseModel, text: String) {
        val productsList = text.trim().split(",")

        viewModelScope.launch(Dispatchers.IO) {
            purchaseRepository.insertProducts(
                purchaseId = purchase.id,
                products = productsList.map {
                    ProductModel(
                        title = it
                    )
                })
        }
    }

    fun changeCategory(purchase: PurchaseModel, categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            purchaseRepository.updatePurchase(
                purchase = purchase.copy(category = purchase.category.copy(id = categoryId)),
            )
        }
    }

    fun removeProduct(product: ProductModel) {
        viewModelScope.launch(Dispatchers.IO) {
            purchaseRepository.deleteProduct(product.id)
        }

//        deletedProducts.add(product)

//        val timer = Timer(
//            delayMillis = SNACKBAR_DURATION,
//            repeatMillis = 0,
//            backgroundWork = {
//                purchaseRepository.deleteProducts(deletedProducts.map { it.id })
//            }
//        )
//
//        timer.startTimer()
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

    /*  fun cancelDeletion() {
          deletedProducts.clear()
      }*/

    companion object {
        const val SNACKBAR_DURATION = 3000L
    }
}