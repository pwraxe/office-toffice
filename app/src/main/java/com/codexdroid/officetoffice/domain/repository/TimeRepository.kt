package com.codexdroid.officetoffice.domain.repository

import kotlinx.coroutines.flow.Flow

interface TimeRepository {
    suspend fun getCheckInTime(): Flow<Long>
    suspend fun getCheckOutTime(): Flow<Long>
    suspend fun saveCheckInTime(time: Long)
    suspend fun saveCheckOutTime(time: Long)

    suspend fun getIsOnboardingDone(): Flow<Boolean>
    suspend fun doOnboarding()
}