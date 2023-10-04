package com.nyx.mypurchases.di

import android.app.Application
import com.nyx.mypurchases.di.modules.ActivityModule
import com.nyx.mypurchases.di.modules.AppModule
import com.nyx.mypurchases.di.modules.CategoryDaoModule
import com.nyx.mypurchases.di.modules.CategoryDatabaseModule
import com.nyx.mypurchases.di.modules.CategoryRepositoryModule
import com.nyx.mypurchases.di.modules.FragmentModule
import com.nyx.mypurchases.ui.createlist.CreateListFragment
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        CategoryDatabaseModule::class,
        CategoryDaoModule::class,
        CategoryRepositoryModule::class,
        ActivityModule::class,
        FragmentModule::class
    ]
)

interface AppComponent {
    fun injectActivity(application: Application)
    fun injectFragment(fragment: CreateListFragment)
}