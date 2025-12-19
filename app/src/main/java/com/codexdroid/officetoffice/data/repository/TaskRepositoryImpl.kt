package com.codexdroid.officetoffice.data.repository

import com.codexdroid.officetoffice.data.datastore.local.dao.TaskDao
import com.codexdroid.officetoffice.data.datastore.local.entity.TaskEntity
import com.codexdroid.officetoffice.data.model.TaskData
import com.codexdroid.officetoffice.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao): TaskRepository {

    override suspend fun insertTask(taskData: TaskData) {
        taskDao.insertTask(taskData.toTaskEntity())
    }

    override suspend fun deleteTask(taskData: TaskData) {
        taskDao.deleteTask(taskData.createdOn)
    }

    override suspend fun updateIsDone(createdOn: Long, isDone: Boolean) {
        taskDao.updateIsDone(createdOn, isDone)
    }

    override suspend fun updateTask(taskData: TaskData) {
        taskDao.updateTask(taskData.createdOn, taskData.task)
    }

    override suspend fun readTasks(): List<TaskData> {
        return taskDao.readTasks().map { it.toTaskData() }
    }
}