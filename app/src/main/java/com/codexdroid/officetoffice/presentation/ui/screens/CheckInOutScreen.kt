package com.codexdroid.officetoffice.presentation.ui.screens

import android.os.VibrationEffect
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.codexdroid.officetoffice.R
import com.codexdroid.officetoffice.data.model.TaskData
import com.codexdroid.officetoffice.presentation.ui.navigations.Screens
import com.codexdroid.officetoffice.presentation.ui.comp_funs.NewTaskDialog
import com.codexdroid.officetoffice.presentation.ui.comp_funs.OnBoardingDialog
import com.codexdroid.officetoffice.presentation.ui.comp_funs.TasksItem
import com.codexdroid.officetoffice.presentation.ui.comp_funs.TaskTimePickerDialog
import com.codexdroid.officetoffice.presentation.ui.comp_funs.YourTasksHeader
import com.codexdroid.officetoffice.presentation.viewmodels.TaskViewModel
import com.codexdroid.officetoffice.utils.AppConstants
import com.codexdroid.officetoffice.utils.AppConstants.getUbuntuFont
import com.codexdroid.officetoffice.utils.AppConstants.to12HourFormat
import com.codexdroid.officetoffice.utils.OnClickEvents
import com.codexdroid.officetoffice.utils.calculateTotalHours
import com.google.gson.Gson
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CheckInOutScreen(
    taskViewModel: TaskViewModel,
    navHostController: NavHostController,
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
    val showTotalHours = (checkInTime  > 0 && checkOutTime > 0) && (abs(checkInTime - checkOutTime) > 0L) && (checkInTime < checkOutTime)


    BackHandler {
        activity?.finish()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(16.dp)
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (showTotalHours) {
                Arrangement.SpaceBetween
            } else {
                Arrangement.Center
            }
        ) {

            // OfficeToffice text
            Text (
                text = "Office Toffice",
                color = Color.Black,
                fontFamily = getUbuntuFont(true),
                fontSize = 26.sp,
                modifier = Modifier
                    .clickable (
                        enabled = true,
                        onClick = {
                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                            taskViewModel.updateCheckInTime(0L)
                            taskViewModel.updateCheckOutTime(0L)
                            taskViewModel.triggerDataEvent(OnClickEvents.EVENT_CLEAR_CHECK_IN_OUT_TIME, "CheckInOutScreen")

                        }
                    )
            )

            // Show totalHours only if valid
            if (showTotalHours) {
                Text(
                    text = calculateTotalHours(checkInTime,checkOutTime),
                    color = Color.Black,
                    fontFamily = getUbuntuFont(true),
                    fontSize = 48.sp,
                )
            }
        }

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
                                    taskViewModel.triggerDataEvent(OnClickEvents.EVENT_CHECK_IN, "CheckInOutScreen")
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

            Row(
                modifier = modifier.weight(1f).padding(horizontal = 6.dp),
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp).clickable(enabled = true, onClick = {
                            navHostController.navigate(Screens.NotificationScreen.route)
                        })

                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_notification),
                        contentDescription = "Notification Icon"
                    )
                    Text(text = "Remind me",
                        fontFamily = getUbuntuFont(false),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        modifier = modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }

            //Spacer(modifier = modifier.weight(1f))

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
                                    taskViewModel.triggerDataEvent(OnClickEvents.EVENT_CHECK_OUT, "CheckInOutScreen")
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
                        taskViewModel.triggerDataEvent(OnClickEvents.EVENT_TASK_COMPLETED, "CheckInOutScreen")
                    },
                    onEditClicked = {
                        taskViewModel.updateSelectedTask(it)
                        taskViewModel.updateOpenNewTaskDialogToEdit(isOpen = true, forEdit = true)
                    },
                    onDeleteClicked = {
                        taskViewModel.deleteTask(it)
                        taskViewModel.triggerDataEvent(OnClickEvents.EVENT_TASK_DELETED, "CheckInOutScreen")
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

            /** Sending Data events to Firestore*/
            val eventName = if (selectedTask.task.isEmpty()) OnClickEvents.EVENT_TASK_DIALOG_OPEN else OnClickEvents.EVENT_TASK_EDITING
            val screenName = if (selectedTask.task.isEmpty()) "CheckInOutScreen/NewTaskDialog" else "CheckInOutScreen/EditTaskDialog"
            taskViewModel.triggerDataEvent(eventName, screenName)

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
                        taskViewModel.triggerDataEvent(OnClickEvents.EVENT_TASK_ADDED, "CheckInOutScreen")
                    }
                    else {
                        //update
                        val taskData = TaskData(selectedTask.id, it, selectedTask.createdOn, selectedTask.isDone)
                        taskViewModel.updateTask(taskData)
                        taskViewModel.triggerDataEvent(OnClickEvents.EVENT_TASK_UPDATED, "CheckInOutScreen")
                    }
                    taskViewModel.updateSelectedTask(TaskData())
                    taskViewModel.updateOpenNewTaskDialogToEdit(isOpen = false, forEdit = false)
                }
            )
        }

        if (!isOnboardingDone) {
            taskViewModel.triggerDataEvent(OnClickEvents.EVENT_INSTRUCTION_DIALOG_OPEN, "CheckInOutScreen/InstructionScreen")
            OnBoardingDialog { taskViewModel.makeOnboardingDone() }
        }
    }
}