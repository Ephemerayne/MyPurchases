package com.nyx.mypurchases.data.products

import androidx.lifecycle.LiveData
import com.nyx.mypurchases.data.entities.ProductRoomEntity

interface ProductDao {

    fun insertProduct(product: ProductRoomEntity)

    fun updateProduct(product: ProductRoomEntity)

    fun insertProducts(products: List<ProductRoomEntity>)

    fun deleteProduct(productId: Int)

    fun deleteProducts()

    fun deleteProducts(productsIds: List<Int>)

    fun getProduct(id: Int): ProductRoomEntity

    fun getPurchaseProducts(purchaseId: Int): LiveData<List<ProductRoomEntity>>

    fun getPurchaseProductsSync(purchaseId: Int): List<ProductRoomEntity>
}