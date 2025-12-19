package com.codexdroid.officetoffice.domain.usecase

import com.codexdroid.officetoffice.domain.repository.TaskRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReadTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend fun readTasks() = flow {
        emit(repository.readTasks())
    }
}