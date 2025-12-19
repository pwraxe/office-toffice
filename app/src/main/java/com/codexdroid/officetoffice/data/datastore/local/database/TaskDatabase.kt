package com.codexdroid.officetoffice.data.datastore.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codexdroid.officetoffice.data.datastore.local.dao.TaskDao
import com.codexdroid.officetoffice.data.datastore.local.entity.TaskEntity


@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var DB_INSTANCE: TaskDatabase? = null

        fun getDatabaseInstance(context: Context): TaskDatabase {

            synchronized(this) {

                if(DB_INSTANCE == null) {
                    DB_INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            TaskDatabase::class.java,
                            name = "db_task").build()
                }
                return DB_INSTANCE!!
            }
        }
    }
}