package com.codexdroid.officetoffice.utils

import android.util.Log
import com.codexdroid.officetoffice.BuildConfig

object Logger {

    private const val MAX_LOG_LENGTH = 4000

    fun debug(tag: String = "OfficeToffice_${BuildConfig.VERSION_NAME}", message: String) {
        if (BuildConfig.DEBUG) {
            if (message.length > MAX_LOG_LENGTH) {
                Log.d(tag, message.take(MAX_LOG_LENGTH))
                debug(tag, message.substring(MAX_LOG_LENGTH))
            } else {
                Log.d(tag, message)
            }
        }
    }

    fun error(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            if (message.length > MAX_LOG_LENGTH) {
                Log.e(tag, message.take(MAX_LOG_LENGTH))
                error(tag, message.substring(MAX_LOG_LENGTH))
            } else {
                Log.e(tag, message)
            }
        }
    }
}
