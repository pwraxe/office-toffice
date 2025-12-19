package com.codexdroid.officetoffice.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UseCaseContainer @Inject constructor(
    val insertTaskUseCase: InsertTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
    val readTasksUseCase: ReadTasksUseCase,
    val updateIsDoneUseCase: UpdateIsDoneUseCase,
    val userOnboardUseCase: UserOnboardingUserCase,

    val getCheckInTimeUseCase: GetCheckInTimeUseCase,
    val getCheckOutTimeUseCase: GetCheckOutTimeUseCase,
    val saveCheckInTimeUseCase: SaveCheckInTimeUseCase,
    val saveCheckOutTimeUseCase: SaveCheckOutTimeUseCase
)