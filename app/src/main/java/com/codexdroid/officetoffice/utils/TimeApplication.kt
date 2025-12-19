package com.codexdroid.officetoffice.utils

import android.app.Application
import com.codexdroid.officetoffice.data.datastore.workers.WorkManagerSetup
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TimeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        WorkManagerSetup(this)
    }
}