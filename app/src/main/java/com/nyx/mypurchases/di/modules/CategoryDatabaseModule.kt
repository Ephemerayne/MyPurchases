package com.nyx.mypurchases.di.modules

import android.app.Application
import com.nyx.mypurchases.data.CategoryDatabase
import dagger.Module
import dagger.Provides

@Module
class CategoryDatabaseModule {

    @Provides
    fun provideDatabase(app: Application): CategoryDatabase = CategoryDatabase.getInstance(app)
}