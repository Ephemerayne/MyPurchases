package com.nyx.mypurchases.di

import android.app.Application
import com.nyx.mypurchases.di.modules.ActivityModule
import com.nyx.mypurchases.di.modules.AppModule
import com.nyx.mypurchases.di.modules.FragmentModule
import com.nyx.mypurchases.di.modules.categories.CategoryDaoModule
import com.nyx.mypurchases.di.modules.categories.CategoryDatabaseModule
import com.nyx.mypurchases.di.modules.categories.CategoryRepositoryModule
import com.nyx.mypurchases.di.modules.purchases.PurchaseDaoModule
import com.nyx.mypurchases.di.modules.purchases.PurchaseDatabaseModule
import com.nyx.mypurchases.di.modules.purchases.PurchaseRepositoryModule
import com.nyx.mypurchases.ui.createlist.CreateListFragment
import com.nyx.mypurchases.ui.main.MainFragment
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        /* Categories */
        CategoryDatabaseModule::class,
        CategoryDaoModule::class,
        CategoryRepositoryModule::class,
        /* Purchases */
        PurchaseDatabaseModule::class,
        PurchaseDaoModule::class,
        PurchaseRepositoryModule::class
    ]
)

interface AppComponent {
    fun injectActivity(application: Application)
    fun injectMainFragment(fragment: MainFragment)
    fun injectCreateListFragment(fragment: CreateListFragment)
}