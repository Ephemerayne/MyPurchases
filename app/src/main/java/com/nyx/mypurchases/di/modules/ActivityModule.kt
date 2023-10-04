package com.nyx.mypurchases.di.modules

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    fun provideActivity() = activity

  /*  @Provides
    fun providePresenter():*/
}