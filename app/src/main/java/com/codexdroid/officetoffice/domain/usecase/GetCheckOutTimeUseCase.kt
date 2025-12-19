package com.codexdroid.officetoffice.domain.usecase

import com.codexdroid.officetoffice.domain.repository.TimeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCheckOutTimeUseCase @Inject constructor(private val repository: TimeRepository) {
    suspend fun getCheckOutTime() = repository.getCheckOutTime()
}