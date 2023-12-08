package com.milovanjakovljevic.targetvideo

import android.app.Application
import timber.log.Timber

class TargetVideoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
