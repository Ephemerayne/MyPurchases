package com.nyx.mypurchases.di.modules.purchases

import android.app.Application
import com.nyx.mypurchases.data.purchases.PurchaseDatabase
import dagger.Module
import dagger.Provides

@Module
class PurchaseDatabaseModule {

    @Provides
    fun provideDatabase(app: Application): PurchaseDatabase =
        PurchaseDatabase.getInstance(app)
}