package com.codexdroid.officetoffice.domain.usecase

import com.codexdroid.officetoffice.data.model.TaskData
import com.codexdroid.officetoffice.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend fun deleteTask(task: TaskData) = repository.deleteTask(task)
}