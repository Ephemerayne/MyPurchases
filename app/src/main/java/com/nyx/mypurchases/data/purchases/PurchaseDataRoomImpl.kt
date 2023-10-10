package com.nyx.mypurchases.data.purchases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nyx.mypurchases.data.entities.PurchaseModelDatabaseEntity

@Dao
interface PurchaseDataRoomImpl: PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun savePurchase(purchase: PurchaseModelDatabaseEntity): Long

    @Update
    override fun updatePurchase(purchase: PurchaseModelDatabaseEntity)

    @Delete
    override fun deletePurchase(purchase: PurchaseModelDatabaseEntity)

    @Query("SELECT * FROM purchase_table")
    override fun getAllPurchases(): List<PurchaseModelDatabaseEntity>
}