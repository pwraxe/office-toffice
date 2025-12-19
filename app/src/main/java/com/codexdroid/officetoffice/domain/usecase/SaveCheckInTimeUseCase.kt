package com.codexdroid.officetoffice.domain.usecase

import com.codexdroid.officetoffice.domain.repository.TimeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveCheckInTimeUseCase @Inject constructor(private val repository: TimeRepository) {
    suspend fun saveCheckInTime(time: Long) = repository.saveCheckInTime(time)
}