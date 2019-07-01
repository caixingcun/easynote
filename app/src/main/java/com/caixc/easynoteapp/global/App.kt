package com.caixc.easynoteapp.global

import android.app.Application
import com.caixc.easynoteapp.base.Preference

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Preference.setContext(applicationContext)
    }

    companion object {
      const val debug = true

    }

}
