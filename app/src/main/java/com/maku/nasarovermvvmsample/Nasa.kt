package com.maku.nasarovermvvmsample

import android.app.Application
import timber.log.Timber

class Nasa : Application() {
    override fun onCreate() {
        super.onCreate()
        //timber
        Timber.plant(Timber.DebugTree())
    }

}