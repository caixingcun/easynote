package com.caixc.easynoteapp.base

import android.content.Context
import android.content.SharedPreferences
import com.caixc.easynoteapp.constant.Constant

class Preference {
    companion object {
        lateinit var preferences:SharedPreferences

        fun setContext(context: Context) {
            preferences = context.getSharedPreferences(context.packageName+Constant.SHARED_NAME,Context.MODE_PRIVATE)
        }
        fun clear(){
            preferences.edit().clear().apply()
        }
    }


}