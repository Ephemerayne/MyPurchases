package com.nyx.mypurchases.di.modules

import com.nyx.mypurchases.data.CategoryDao
import com.nyx.mypurchases.data.CategoryDatabase
import dagger.Module
import dagger.Provides

@Module
class CategoryDaoModule {

    @Provides
    fun provideDao(categoryDatabase: CategoryDatabase): CategoryDao =
        categoryDatabase.categoryDao()
}