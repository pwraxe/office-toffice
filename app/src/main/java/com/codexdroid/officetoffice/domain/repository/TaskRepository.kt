package com.codexdroid.officetoffice.domain.repository

import com.codexdroid.officetoffice.data.datastore.local.entity.TaskEntity
import com.codexdroid.officetoffice.data.model.TaskData
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(taskData: TaskData)
    suspend fun readTasks(): List<TaskData>
    suspend fun updateTask(taskData: TaskData)
    suspend fun deleteTask(taskData: TaskData)
    suspend fun updateIsDone(createdOn: Long, isDone: Boolean)
}