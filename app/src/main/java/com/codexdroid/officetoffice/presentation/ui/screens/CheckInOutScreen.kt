package com.codexdroid.officetoffice.presentation.ui.screens

import android.os.VibrationEffect
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codexdroid.officetoffice.data.model.TaskData
import com.codexdroid.officetoffice.presentation.ui.comp_funs.NewTaskDialog
import com.codexdroid.officetoffice.presentation.ui.comp_funs.OnBoardingDialog
import com.codexdroid.officetoffice.presentation.ui.comp_funs.TasksItem
import com.codexdroid.officetoffice.presentation.ui.comp_funs.TaskTimePickerDialog
import com.codexdroid.officetoffice.presentation.ui.comp_funs.YourTasksHeader
import com.codexdroid.officetoffice.presentation.viewmodels.TaskViewModel
import com.codexdroid.officetoffice.utils.AppConstants
import com.codexdroid.officetoffice.utils.AppConstants.getUbuntuFont
import com.codexdroid.officetoffice.utils.AppConstants.to12HourFormat
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CheckInOutScreen(
    taskViewModel: TaskViewModel,
    modifier: Modifier = Modifier) {

    val checkInTime by taskViewModel.checkInTime.collectAsState()
    val checkOutTime by taskViewModel.checkOutTime.collectAsState()
    val timeFor by taskViewModel.timeFor.collectAsState()
    val tasks by taskViewModel.tasks.collectAsState()
    val isOnboardingDone by taskViewModel.isOnboardingDone.collectAsState()
    val openNewTaskDialog by taskViewModel.openNewTaskDialogToEdit.collectAsState()

    val context = LocalContext.current
    val activity = LocalActivity.current
    val vibrator = AppConstants.getVibratorService(context)
    val selectedTask by taskViewModel.selectedTask.collectAsState()


    BackHandler {
        activity?.finish()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {

        CenterAlignedTopAppBar(
            title = {
                Text (
                    text = "Office Toffice",
                    color = Color.Black,
                    fontFamily = getUbuntuFont(true),
                    modifier = modifier.combinedClickable (
                        enabled = true,
                        onClick = {
                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                            taskViewModel.updateCheckInTime(0L)
                            taskViewModel.updateCheckOutTime(0L)
                        }
                    )
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White // Set the background color to white
            )
        )

        Row (modifier = modifier.padding(top = 20.dp)) {
            Column {
                AppConstants.RegularText("Check-In")

                Row {

                    Text(
                        text = checkInTime.to12HourFormat(),
                        fontSize = 40.sp,
                        fontFamily = getUbuntuFont(true),
                        modifier = modifier
                            .combinedClickable(
                                enabled = true,
                                onClick = {
                                    taskViewModel.updateCheckInTime(System.currentTimeMillis())
                                },
                                onLongClick = {
                                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                                    taskViewModel.updateTimeFor("AM")
                                }
                            )
                    )
                    AppConstants.RegularText("AM")
                }
            }

            Spacer(modifier = modifier.weight(1f))

            Column {
                AppConstants.RegularText("Check-Out")

                Row {

                    Text(
                        text = checkOutTime.to12HourFormat(),
                        fontSize = 40.sp,
                        fontFamily = getUbuntuFont(true),
                        modifier = modifier
                            .combinedClickable (
                                enabled = true,
                                onClick = {
                                    taskViewModel.updateCheckOutTime(System.currentTimeMillis())
                                },
                                onLongClick = {
                                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                                    taskViewModel.updateTimeFor("PM")
                                }
                            )
                    )
                    AppConstants.RegularText("PM")
                }
            }
        }

        YourTasksHeader()

        LazyColumn {
            items(tasks) { task ->
                TasksItem(
                    task,
                    onCheckClicked = {
                        taskViewModel.updateIsDone(it)
                    },
                    onEditClicked = {
                        taskViewModel.updateSelectedTask(it)
                        taskViewModel.updateOpenNewTaskDialogToEdit(isOpen = true, forEdit = true)
                    },
                    onDeleteClicked = {
                        taskViewModel.deleteTask(it)
                    }
                )
            }
        }

        if (timeFor.isNotEmpty()) {
            TaskTimePickerDialog(
                timeFor,
                onDismissRequest = {
                    taskViewModel.updateTimeFor("")
                },
                onTimeSelected = {
                    if (timeFor == "AM") taskViewModel.updateCheckInTime(it)
                    else taskViewModel.updateCheckOutTime(it)
                    taskViewModel.updateTimeFor("")
                }
            )
        }

        if (openNewTaskDialog.first) {
            Log.d("AXE","SelectedTask: ${Gson().toJson(selectedTask)}")

            NewTaskDialog(
                taskData = selectedTask,
                onDismiss = {
                    taskViewModel.updateOpenNewTaskDialogToEdit(isOpen = false, forEdit = openNewTaskDialog.second)
                }, onConfirm = {

                    //check is for adding or editing

                    if (selectedTask.task.isEmpty()) {
                        //New Add
                        val taskData = TaskData(0, it, System.currentTimeMillis(), false)
                        taskViewModel.insertTask(taskData)
                    }
                    else {
                        //update
                        val taskData = TaskData(selectedTask.id, it, selectedTask.createdOn, selectedTask.isDone)
                        taskViewModel.updateTask(taskData)
                    }
                    taskViewModel.updateSelectedTask(TaskData())
                    taskViewModel.updateOpenNewTaskDialogToEdit(isOpen = false, forEdit = false)
                }
            )
        }

        if (!isOnboardingDone) {
            OnBoardingDialog { taskViewModel.makeOnboardingDone() }
        }
    }
}