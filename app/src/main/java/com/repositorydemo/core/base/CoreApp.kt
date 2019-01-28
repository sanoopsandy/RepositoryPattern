package com.repositorydemo.core.base

import android.app.Application
import com.repositorydemo.core.di.AppModule
import com.repositorydemo.core.di.CoreComponent
import com.repositorydemo.core.di.DaggerCoreComponent

class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        coreComponent = DaggerCoreComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}