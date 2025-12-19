package com.codexdroid.officetoffice.data.datastore.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


class WorkManagerSetup(context: Context) {
    init {
        setupDailyReset(context)
        setupDailyReminder(context)
    }

    private fun setupDailyReset(context: Context) {
        val resetWorkRequest = PeriodicWorkRequestBuilder<ResetTimesWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(1, 0), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "reset_times",
            ExistingPeriodicWorkPolicy.REPLACE,
            resetWorkRequest
        )
    }

    private fun setupDailyReminder(context: Context) {
        val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(21, 0), TimeUnit.MILLISECONDS)
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_reminder",
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationWorkRequest
        )
    }

    private fun calculateInitialDelay(targetHour: Int, targetMinute: Int): Long {
        val now = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = now
            set(java.util.Calendar.HOUR_OF_DAY, targetHour)
            set(java.util.Calendar.MINUTE, targetMinute)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }

        if (calendar.timeInMillis <= now) {
            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1)
        }

        return calendar.timeInMillis - now
    }
}