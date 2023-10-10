package com.nyx.mypurchases.di.modules.categories

import android.app.Application
import com.nyx.mypurchases.data.categories.CategoryDatabase
import dagger.Module
import dagger.Provides

@Module
class CategoryDatabaseModule {

    @Provides
    fun provideDatabase(app: Application): CategoryDatabase = CategoryDatabase.getInstance(app)
}