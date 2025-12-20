package com.codexdroid.officetoffice.utils

import android.app.Application
import androidx.work.Configuration
import com.codexdroid.officetoffice.data.datastore.workers.WorkManagerSetup
import com.codexdroid.officetoffice.data.preferences.SharePreference
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TimeApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        SharePreference.init(this)
        WorkManagerSetup(this)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}