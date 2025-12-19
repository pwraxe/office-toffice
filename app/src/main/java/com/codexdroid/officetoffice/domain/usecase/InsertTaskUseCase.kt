package com.codexdroid.officetoffice.domain.usecase

import com.codexdroid.officetoffice.data.model.TaskData
import com.codexdroid.officetoffice.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend fun insertTask(task: TaskData) = repository.insertTask(task)
}