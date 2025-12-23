package com.codexdroid.officetoffice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codexdroid.officetoffice.data.datastore.workers.WorkerFactory
import com.codexdroid.officetoffice.data.model.TaskData
import com.codexdroid.officetoffice.domain.usecase.UseCaseContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val useCaseContainer: UseCaseContainer,
    private val workerFactory: WorkerFactory
) : ViewModel() {

    private val _checkInTime = MutableStateFlow(0L)
    val checkInTime = _checkInTime.asStateFlow()

    private val _checkOutTime = MutableStateFlow(0L)
    val checkOutTime = _checkOutTime.asStateFlow()

    private val _timeFor = MutableStateFlow("")
    val timeFor = _timeFor.asStateFlow()

    private val _tasks = MutableStateFlow(mutableListOf<TaskData>())
    val tasks = _tasks.asStateFlow()

    private val _openTimePicker = MutableStateFlow(false)
    val openTimePicker = _openTimePicker.asStateFlow()

    private val _isOnboardingDone = MutableStateFlow(false)
    val isOnboardingDone = _isOnboardingDone.asStateFlow()

    private val _openNewTaskDialogToEdit = MutableStateFlow(false to false)
    val openNewTaskDialogToEdit = _openNewTaskDialogToEdit.asStateFlow()

    private val _selectedTask = MutableStateFlow(TaskData())
    val selectedTask = _selectedTask.asStateFlow()

    private val _showTotalHours = MutableStateFlow(false)
    val showTotalHours = _showTotalHours.asStateFlow()

    init {
        viewModelScope.launch {
            useCaseContainer.getCheckInTimeUseCase.getCheckInTime().collectLatest {
                _checkInTime.value = it
            }
        }
        viewModelScope.launch {
            useCaseContainer.getCheckOutTimeUseCase.getCheckOutTime().collectLatest {
                _checkOutTime.value = it
            }
        }

        viewModelScope.launch {
            useCaseContainer.readTasksUseCase.readTasks().collectLatest {
                _tasks.value = it.toMutableList()
            }
        }

        viewModelScope.launch {
            useCaseContainer.userOnboardUseCase.getIsOnboardingDone().collectLatest {
                _isOnboardingDone.value = it
            }
        }
    }

    fun updateCheckInTime(time: Long) {
        _checkInTime.value = time
        viewModelScope.launch {
            useCaseContainer.saveCheckInTimeUseCase.saveCheckInTime(time)
        }
        updateShowTotalHours()
    }

    fun updateCheckOutTime(time: Long) {
        _checkOutTime.value = time
        viewModelScope.launch {
            useCaseContainer.saveCheckOutTimeUseCase.saveCheckOutTime(time)
        }
        updateShowTotalHours()
    }

    fun updateTimeFor(timeFor: String) {
        _timeFor.value = timeFor
    }

    fun insertTask(taskData: TaskData) {
        _tasks.value = _tasks.value.toMutableList().apply { add(taskData) }
        viewModelScope.launch {
            useCaseContainer.insertTaskUseCase.insertTask(taskData)
        }
    }

    fun deleteTask(taskData: TaskData) {
        _tasks.value = _tasks.value.filter { it.createdOn != taskData.createdOn }.toMutableList()
        viewModelScope.launch {
            useCaseContainer.deleteTaskUseCase.deleteTask(taskData)
        }
    }

    fun updateTask(taskData: TaskData) {
        _tasks.value = _tasks.value.map {
            if (it.createdOn == taskData.createdOn) {
                taskData
            } else it
        }.toMutableList()

        viewModelScope.launch {
            useCaseContainer.updateTaskUseCase.updateTask(taskData)
        }
    }

    fun updateIsDone(taskData: TaskData) {
        _tasks.value = _tasks.value.map {
            if (it.createdOn == taskData.createdOn) {
                it.copy(isDone = !it.isDone)
            } else it
        }.toMutableList()

        viewModelScope.launch {
            useCaseContainer.updateIsDoneUseCase.updateIsDone(taskData.createdOn, !taskData.isDone)
        }
    }

    fun updateOpenNewTaskDialogToEdit(isOpen: Boolean, forEdit: Boolean) {
        _openNewTaskDialogToEdit.value = isOpen to forEdit
    }

    fun updateSelectedTask(taskData: TaskData) {
        _selectedTask.value = taskData
    }

    fun makeOnboardingDone() {
        _isOnboardingDone.value = true
        viewModelScope.launch {
            useCaseContainer.userOnboardUseCase.doOnboarding()
        }
    }

    fun makeOnboardingUnDone() {
        _isOnboardingDone.value = false
    }

    /** To show total hours if valid time entered*/
    private fun updateShowTotalHours() {
        _showTotalHours.value = (checkInTime.value > 0 && checkOutTime.value > 0) && (abs(checkInTime.value - checkOutTime.value) > 0L) && (checkInTime.value < checkOutTime.value)
    }

    fun triggerDataEvent(eventName: String, screenName: String) {
        workerFactory.createWorker(eventName, screenName)
    }
}