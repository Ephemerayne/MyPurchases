package com.nyx.mypurchases.data.purchases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nyx.mypurchases.data.entities.ProductRoomEntity
import com.nyx.mypurchases.data.entities.PurchaseRoomEntity
import com.nyx.mypurchases.data.products.ProductDaoRoomImpl
import com.nyx.mypurchases.domain.converters.Converters

@Database(
    entities = [
        PurchaseRoomEntity::class,
        ProductRoomEntity::class
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class PurchaseDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDaoRoomImpl
    abstract fun productDao(): ProductDaoRoomImpl

    companion object {

        @Volatile
        private var INSTANCE: PurchaseDatabase? = null

        fun getInstance(context: Context): PurchaseDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): PurchaseDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PurchaseDatabase::class.java, "purchases.db"
            ).build()
        }
    }
}