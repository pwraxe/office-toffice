package com.codexdroid.officetoffice.data.datastore.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.codexdroid.officetoffice.data.datastore.DataStoreManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//TODO: This class is  getting failed

@HiltWorker
class ResetTimesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val dataStoreManager: DataStoreManager
) : CoroutineWorker(context, params) {



    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            dataStoreManager.saveCheckInTime(0L)
            dataStoreManager.saveCheckOutTime(0L)
            Log.d("AXE", "Times reset successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e("AXE", "Error resetting times", e)
            Result.failure()
        }
    }
}