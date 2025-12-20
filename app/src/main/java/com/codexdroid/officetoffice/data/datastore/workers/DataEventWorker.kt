package com.codexdroid.officetoffice.data.datastore.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.codexdroid.officetoffice.utils.AppConstants
import com.codexdroid.officetoffice.utils.Logger
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DataEventWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val firestore: FirebaseFirestore
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        
        val eventName = inputData.getString(KEY_EVENT_NAME)
        val screenName = inputData.getString(KEY_SCREEN_NAME)
        val time = inputData.getString(KEY_TIME)
        val deviceName = inputData.getString(KEY_DEVICE_NAME)
        val deviceUniqueId = inputData.getString(KEY_DEVICE_UNIQUE_ID)

        Logger.debug("DataEventWorker", "Processing Event: Name=$eventName, Screen=$screenName, Time=$time, Device=$deviceName, ID=$deviceUniqueId")

        if (deviceUniqueId != null) {
            val data = hashMapOf(
                "time" to time,
                "eventName" to eventName,
                "screenName" to screenName,
                "deviceName" to deviceName
            )

            try {
                Tasks.await(
                    firestore.collection("DeviceEvents")
                        .document(deviceUniqueId)
                        .collection(deviceName.toString())
                        .document("$time")
                        .set(data)
                )
            } catch (e: Exception) {
                Tasks.await(
                    firestore.collection("Exceptions")
                        .document(deviceUniqueId)
                        .collection(deviceName.toString())
                        .document("$time")
                        .set("message" to "${e.message}")
                )
                return@withContext Result.failure()
            }
        } else {
            Logger.debug("DataEventWorker", "DeviceUniqueId is null, skipping Firestore upload")
        }

        return@withContext Result.success()
    }

    companion object {

        const val KEY_EVENT_NAME = "event_name"
        const val KEY_SCREEN_NAME = "screen_name"
        const val KEY_TIME = "time"
        const val KEY_DEVICE_NAME = "device_name"
        const val KEY_DEVICE_UNIQUE_ID = "device_unique_id"

        fun createInputData(
            context: Context,
            eventName: String,
            screenName: String
        ): Data {
            return Data.Builder()
                .putString(KEY_EVENT_NAME, eventName)
                .putString(KEY_SCREEN_NAME, screenName)
                .putString(KEY_TIME, AppConstants.formatTime(System.currentTimeMillis(),withSecond = true))
                .putString(KEY_DEVICE_NAME, AppConstants.getDeviceName())
                .putString(KEY_DEVICE_UNIQUE_ID, AppConstants.getDeviceUniqueId(context))
                .build()
        }
    }
}