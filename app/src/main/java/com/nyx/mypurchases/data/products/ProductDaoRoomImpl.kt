package com.nyx.mypurchases.data.products

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nyx.mypurchases.data.entities.ProductRoomEntity

@Dao
interface ProductDaoRoomImpl : ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertProduct(product: ProductRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertProducts(products: List<ProductRoomEntity>)

    @Update
    override fun updateProduct(product: ProductRoomEntity)

    @Update
    override fun updateProducts(products: List<ProductRoomEntity>)

    @Query("DELETE FROM product_table WHERE id=:productId")
    override fun deleteProduct(productId: Long)

    @Query("DELETE FROM product_table")
    override fun deleteProducts()

    @Query("SELECT * FROM product_table WHERE id=:id")
    override fun getProduct(id: Int): ProductRoomEntity

    @Query("SELECT * FROM product_table WHERE purchaseId=:purchaseId")
    override fun getPurchaseProducts(purchaseId: Int): LiveData<List<ProductRoomEntity>>

    @Query("SELECT * FROM product_table WHERE purchaseId=:purchaseId")
    override fun getPurchaseProductsSync(purchaseId: Int): List<ProductRoomEntity>

    @Query("DELETE FROM product_table WHERE id IN (:productsIds)")
    override fun deleteProducts(productsIds: List<Int>)
}