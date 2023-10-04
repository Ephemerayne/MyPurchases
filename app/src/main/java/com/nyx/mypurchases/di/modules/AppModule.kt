package com.nyx.mypurchases.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideAppContext(): Application = application
}