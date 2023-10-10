package com.nyx.mypurchases.di.modules.categories

import com.nyx.mypurchases.data.categories.CategoryDao
import com.nyx.mypurchases.data.categories.CategoryDatabase
import dagger.Module
import dagger.Provides

@Module
class CategoryDaoModule {

    @Provides
    fun provideDao(categoryDatabase: CategoryDatabase): CategoryDao =
        categoryDatabase.categoryDao()
}