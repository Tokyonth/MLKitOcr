package com.tokyonth.mlkitocr

import android.app.Application

class App : Application() {

    companion object {
        lateinit var baseApp: Application
    }

    override fun onCreate() {
        super.onCreate()
        baseApp = this
    }

}
