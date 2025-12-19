package com.codexdroid.officetoffice.data.model

import com.codexdroid.officetoffice.data.datastore.local.entity.TaskEntity

data class TaskData(
    val id: Int = 0,
    val task: String = "",
    val createdOn: Long = System.currentTimeMillis(),
    var isDone: Boolean = false
) {
    fun toTaskEntity() = TaskEntity(
        id = id,
        task = task,
        createdOn = createdOn,
        isDone = isDone
    )

    fun updateIsDone(isDone: Boolean) = TaskData(
        id = id,
        task = task,
        createdOn = createdOn,
        isDone = isDone
    )
}