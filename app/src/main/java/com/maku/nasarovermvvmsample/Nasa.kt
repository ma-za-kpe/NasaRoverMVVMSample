package com.maku.nasarovermvvmsample

import android.app.Application
import com.maku.logging.Logger

class Nasa: Application() {

    // initiate analytics, crashlytics, etc

    override fun onCreate() {
        super.onCreate()

        initLogger()
    }

    private fun initLogger() {
        Logger.init()
    }
}