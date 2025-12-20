package com.codexdroid.officetoffice.data.datastore.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.codexdroid.officetoffice.data.datastore.local.entity.TaskEntity

@Dao
interface TaskDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Transaction
    @Query("SELECT * FROM task_table ORDER BY createdOn DESC")
    suspend fun readTasks(): List<TaskEntity>


    @Transaction
    @Query("UPDATE task_table SET task = :task WHERE createdOn = :createdOn")
    suspend fun updateTask(createdOn: Long, task: String)

    @Transaction
    @Query("DELETE FROM task_table WHERE createdOn = :createdOn")
    suspend fun deleteTask(createdOn: Long)

    @Transaction
    @Query("UPDATE task_table SET isDone = :isDone WHERE createdOn = :createdOn")
    suspend fun updateIsDone(createdOn: Long, isDone: Boolean)
}