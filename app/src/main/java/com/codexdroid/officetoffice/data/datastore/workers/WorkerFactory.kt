package com.codexdroid.officetoffice.data.datastore.workers

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerFactory @Inject constructor(
    private val context: Context
) {

    fun createWorker(eventName: String, screenName: String) {
        val workRequest = OneTimeWorkRequestBuilder<DataEventWorker>()
            .setInputData(
                DataEventWorker.createInputData(
                    context = context,
                    eventName = eventName,
                    screenName = screenName
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}