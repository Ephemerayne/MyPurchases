package com.nyx.mypurchases.di.modules

import com.nyx.mypurchases.data.CategoryDao
import com.nyx.mypurchases.data.CategoryRepositoryImpl
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import dagger.Module
import dagger.Provides

@Module
class CategoryRepositoryModule {

    @Provides
    fun provideRepository(categoryDao: CategoryDao): CategoryRepository =
        CategoryRepositoryImpl(categoryDao)
}