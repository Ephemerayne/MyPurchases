package com.nyx.mypurchases.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [CategoryChipModel::class], version = 1)
abstract class CategoriesDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDataRoomImpl

    companion object {

        val PREPOPULATE_DATA = listOf(
            CategoryChipModel(0, "Смешанное"),
            CategoryChipModel(0, "Продукты"),
            CategoryChipModel(0, "Косметика"),
            CategoryChipModel(0, "Аптека"),
            CategoryChipModel(0, "Для дома"),
            CategoryChipModel(0, "Ремонт"),
        )

        @Volatile
        private var INSTANCE: CategoriesDatabase? = null

        fun getInstance(context: Context): CategoriesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CategoriesDatabase::class.java, "categories.db"
            )
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        CoroutineScope(Dispatchers.IO).launch {
                            val categoryDao = getInstance(context).categoryDao()
                            PREPOPULATE_DATA.forEach {
                                categoryDao.insertCategory(it)
                            }
                        }
                    }
                })
                .build()
    }
}