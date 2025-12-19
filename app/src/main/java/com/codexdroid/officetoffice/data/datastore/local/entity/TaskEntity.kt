package com.codexdroid.officetoffice.data.datastore.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codexdroid.officetoffice.data.model.TaskData

@Entity(tableName = "task_table")
class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val task: String,
    val createdOn: Long,
    val isDone: Boolean = false
)  {
    fun toTaskData() = TaskData(
        id = id,
        task = task,
        createdOn = createdOn,
        isDone = isDone
    )
}