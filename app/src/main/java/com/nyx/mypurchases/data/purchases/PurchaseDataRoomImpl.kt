package com.nyx.mypurchases.data.purchases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nyx.mypurchases.data.entities.PurchaseRoomEntity

@Dao
interface PurchaseDataRoomImpl: PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun savePurchase(purchase: PurchaseRoomEntity): Long

    @Update
    override fun updatePurchase(purchase: PurchaseRoomEntity)

    @Delete
    override fun deletePurchase(purchase: PurchaseRoomEntity)

    @Query("SELECT * FROM purchase_table")
    override fun getAllPurchases(): List<PurchaseRoomEntity>
}