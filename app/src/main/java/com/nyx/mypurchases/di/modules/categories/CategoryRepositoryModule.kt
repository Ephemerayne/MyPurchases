package com.nyx.mypurchases.di.modules.categories

import com.nyx.mypurchases.data.categories.CategoryDao
import com.nyx.mypurchases.data.categories.CategoryRepositoryImpl
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import dagger.Module
import dagger.Provides

@Module
class CategoryRepositoryModule {

    @Provides
    fun provideRepository(categoryDao: CategoryDao): CategoryRepository =
        CategoryRepositoryImpl(categoryDao)
}