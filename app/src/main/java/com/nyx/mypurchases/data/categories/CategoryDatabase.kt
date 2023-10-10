package com.nyx.mypurchases.data.categories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nyx.mypurchases.domain.converters.Converters
import com.nyx.mypurchases.domain.entity.CategoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [CategoryModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class CategoryDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDataRoomImpl

    companion object {

        val PREPOPULATE_DATA = listOf(
            CategoryModel(0, "Смешанная"),
            CategoryModel(0, "Продукты"),
            CategoryModel(0, "Одежда"),
            CategoryModel(0, "Косметика"),
            CategoryModel(0, "Бытовая химия"),
            CategoryModel(0, "Аптека"),
            CategoryModel(0, "Для дома"),
        )

        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        fun getInstance(context: Context): CategoryDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): CategoryDatabase {
            val roomDb = Room.databaseBuilder(
                context.applicationContext,
                CategoryDatabase::class.java, "categories.db"
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

            // TODO fix prepopulate data
            CoroutineScope(Dispatchers.IO).launch {
                roomDb.runInTransaction {}
            }
            return roomDb
        }
    }
}
