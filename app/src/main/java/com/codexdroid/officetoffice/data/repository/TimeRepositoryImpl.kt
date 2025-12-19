package com.codexdroid.officetoffice.data.repository

import com.codexdroid.officetoffice.data.datastore.DataStoreManager
import com.codexdroid.officetoffice.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow


class TimeRepositoryImpl(private val dataStoreManager: DataStoreManager): TimeRepository {

    override suspend fun saveCheckInTime(time: Long) {
        dataStoreManager.saveCheckInTime(time)
    }

    override suspend fun saveCheckOutTime(time: Long) {
        dataStoreManager.saveCheckOutTime(time)
    }

    override suspend fun getCheckInTime(): Flow<Long> {
        return dataStoreManager.getCheckInTime()
    }

    override suspend fun getCheckOutTime(): Flow<Long> {
        return dataStoreManager.getCheckOutTime()
    }

    override suspend fun getIsOnboardingDone(): Flow<Boolean> {
        return dataStoreManager.getIsOnboardingDone()
    }

    override suspend fun doOnboarding() {
        dataStoreManager.doOnboardingDone()
    }
}