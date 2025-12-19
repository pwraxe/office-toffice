package com.codexdroid.officetoffice.di

import android.content.Context
import com.codexdroid.officetoffice.data.datastore.DataStoreManager
import com.codexdroid.officetoffice.data.datastore.local.dao.TaskDao
import com.codexdroid.officetoffice.data.datastore.local.database.TaskDatabase
import com.codexdroid.officetoffice.data.repository.TaskRepositoryImpl
import com.codexdroid.officetoffice.data.repository.TimeRepositoryImpl
import com.codexdroid.officetoffice.domain.repository.TaskRepository
import com.codexdroid.officetoffice.domain.repository.TimeRepository
import com.codexdroid.officetoffice.domain.usecase.DeleteTaskUseCase
import com.codexdroid.officetoffice.domain.usecase.GetCheckInTimeUseCase
import com.codexdroid.officetoffice.domain.usecase.GetCheckOutTimeUseCase
import com.codexdroid.officetoffice.domain.usecase.InsertTaskUseCase
import com.codexdroid.officetoffice.domain.usecase.ReadTasksUseCase
import com.codexdroid.officetoffice.domain.usecase.SaveCheckInTimeUseCase
import com.codexdroid.officetoffice.domain.usecase.SaveCheckOutTimeUseCase
import com.codexdroid.officetoffice.domain.usecase.UpdateIsDoneUseCase
import com.codexdroid.officetoffice.domain.usecase.UpdateTaskUseCase
import com.codexdroid.officetoffice.domain.usecase.UserOnboardingUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Provide UseCases
    @Provides
    @Singleton
    fun provideInsertTaskUseCase(repository: TaskRepository): InsertTaskUseCase {
        return InsertTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateTaskUseCase(repository: TaskRepository): UpdateTaskUseCase {
        return UpdateTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideReadTasksUseCase(repository: TaskRepository): ReadTasksUseCase {
        return ReadTasksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCheckInTimeUseCase(repository: TimeRepository): GetCheckInTimeUseCase {
        return GetCheckInTimeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCheckOutTimeUseCase(repository: TimeRepository): GetCheckOutTimeUseCase {
        return GetCheckOutTimeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveCheckInTimeUseCase(repository: TimeRepository): SaveCheckInTimeUseCase {
        return SaveCheckInTimeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveCheckOutTimeUseCase(repository: TimeRepository): SaveCheckOutTimeUseCase {
        return SaveCheckOutTimeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateIsDoneUseCase(repository: TaskRepository): UpdateIsDoneUseCase {
        return UpdateIsDoneUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUserForFirstTimeUseCase(repository: TimeRepository): UserOnboardingUserCase {
        return UserOnboardingUserCase(repository)
    }

    //Database instance
    @Provides
    @Singleton
    fun providesDatabaseInstance(@ApplicationContext context: Context): TaskDatabase {
        return TaskDatabase.getDatabaseInstance(context)
    }

    //Provide Dao
    @Provides
    @Singleton
    fun providesTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()
    }

    //Provide Repository
    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideTimeRepository(dataStoreManager: DataStoreManager): TimeRepository {
        return TimeRepositoryImpl(dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

}