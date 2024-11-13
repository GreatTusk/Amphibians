package com.f776.amphibians

import android.app.Application
import com.f776.amphibians.data.AppContainer
import com.f776.amphibians.data.DefaultAppContainer

class AmphibianApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}