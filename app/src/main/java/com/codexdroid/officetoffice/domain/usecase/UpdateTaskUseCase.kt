package com.codexdroid.officetoffice.domain.usecase

import com.codexdroid.officetoffice.data.model.TaskData
import com.codexdroid.officetoffice.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend fun updateTask(task: TaskData) = repository.updateTask(task)
}