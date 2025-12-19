package com.codexdroid.officetoffice.domain.usecase

import com.codexdroid.officetoffice.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserOnboardingUserCase @Inject constructor(private val repository: TimeRepository) {
    suspend fun doOnboarding() = repository.doOnboarding()
    suspend fun getIsOnboardingDone(): Flow<Boolean> {
        return repository.getIsOnboardingDone()
    }
}

