package com.codexdroid.officetoffice.domain.usecase

import com.codexdroid.officetoffice.domain.repository.TimeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCheckInTimeUseCase @Inject constructor(private val repository: TimeRepository) {
    suspend fun getCheckInTime() = repository.getCheckInTime()
}