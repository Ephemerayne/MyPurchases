package com.nyx.mypurchases.di.modules.purchases

import com.nyx.mypurchases.data.purchases.PurchaseDao
import com.nyx.mypurchases.data.purchases.PurchaseDatabase
import dagger.Module
import dagger.Provides

@Module
class PurchaseDaoModule {

    @Provides
    fun provideDao(purchaseDatabase: PurchaseDatabase): PurchaseDao =
        purchaseDatabase.purchaseDao()
}