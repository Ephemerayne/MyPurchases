package com.nyx.mypurchases.di.modules.purchases

import com.nyx.mypurchases.data.categories.CategoryDao
import com.nyx.mypurchases.data.purchases.PurchaseDao
import com.nyx.mypurchases.data.purchases.PurchaseRepositoryImpl
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import dagger.Module
import dagger.Provides

@Module
class PurchaseRepositoryModule {

    @Provides
    fun provideRepository(purchaseDao: PurchaseDao, categoryDao: CategoryDao): PurchaseRepository =
        PurchaseRepositoryImpl(purchaseDao, categoryDao)
}