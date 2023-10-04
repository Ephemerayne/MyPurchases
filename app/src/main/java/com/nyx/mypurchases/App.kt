package com.nyx.mypurchases

import android.app.Application
import com.nyx.mypurchases.di.AppComponent
import com.nyx.mypurchases.di.DaggerAppComponent
import com.nyx.mypurchases.di.modules.AppModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()
    }

    private fun setup() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this)).build()
        appComponent.injectActivity(this)
    }

    companion object {
        lateinit var instance: App private set
        lateinit var appComponent: AppComponent
    }
}