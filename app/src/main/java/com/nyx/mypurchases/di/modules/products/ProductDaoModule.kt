package com.nyx.mypurchases.di.modules.products

import com.nyx.mypurchases.data.products.ProductDao
import com.nyx.mypurchases.data.purchases.PurchaseDatabase
import dagger.Module
import dagger.Provides

@Module
class ProductDaoModule {

    @Provides
    fun provideDao(purchaseDatabase: PurchaseDatabase): ProductDao =
        purchaseDatabase.productDao()
}