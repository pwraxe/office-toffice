package com.codexdroid.officetoffice.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharePreference {

    private const val PREF_NAME = "OfficeTofficePref"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}