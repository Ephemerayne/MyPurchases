package com.nyx.mypurchases.data.purchases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nyx.mypurchases.data.entities.PurchaseRoomEntity

@Dao
interface PurchaseDataRoomImpl : PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun savePurchase(purchase: PurchaseRoomEntity): Long

    @Update
    override fun updatePurchase(purchase: PurchaseRoomEntity)

    @Query("DELETE FROM purchase_table WHERE id=:purchaseId")
    override fun deletePurchase(purchaseId: Long)

    @Query("SELECT * FROM purchase_table")
    override fun getAllPurchases(): List<PurchaseRoomEntity>
}