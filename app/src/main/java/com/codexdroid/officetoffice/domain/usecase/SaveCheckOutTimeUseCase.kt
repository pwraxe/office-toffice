package com.codexdroid.officetoffice.domain.usecase

import com.codexdroid.officetoffice.domain.repository.TimeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveCheckOutTimeUseCase @Inject constructor(private val repository: TimeRepository) {
    suspend fun saveCheckOutTime(time: Long) = repository.saveCheckOutTime(time)
}