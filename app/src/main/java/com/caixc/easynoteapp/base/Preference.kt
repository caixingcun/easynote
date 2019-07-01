package com.caixc.easynoteapp.base

import android.content.Context
import android.content.SharedPreferences
import com.caixc.easynoteapp.constant.Constant
import java.lang.IllegalArgumentException

class Preference {

    companion object {
        lateinit var preferences: SharedPreferences

        fun setContext(context: Context) {
            preferences = context.getSharedPreferences(context.packageName + Constant.SHARED_NAME, Context.MODE_PRIVATE)
        }

        fun clear() {
            preferences.edit().clear().apply()
        }
    }

    fun <T> getValue(key: String, default: T): T = getPreference(key, default)

    fun <T> setValue(key: String, value: T) = putPreference(key, value)


    private fun <T> getPreference(name: String, default: T): T = with(preferences) {
        val res = when (default) {
            is Long -> getLong(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            is String -> getString(name, default)
            else -> throw IllegalArgumentException("this type can not be saved into preferences")
        }
        res as T
    }

    private fun <T> putPreference(name: String, value: T) = with(preferences.edit()) {
        val res = when (value) {
            is Long -> putLong(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            is String -> putString(name, value)
            else -> throw IllegalArgumentException("this type can not be saved into preferences")
        }.apply()
    }
}