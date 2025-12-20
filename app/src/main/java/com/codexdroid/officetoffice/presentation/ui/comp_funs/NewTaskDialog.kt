package com.codexdroid.officetoffice.presentation.ui.comp_funs

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codexdroid.officetoffice.data.model.TaskData
import com.codexdroid.officetoffice.utils.AppConstants
import com.google.gson.Gson

@Composable
fun NewTaskDialog(
    taskData: TaskData = TaskData(),
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    Log.d("AXe","Data in Dialong : ${Gson().toJson(taskData)}")
    val isForAdd = taskData.task.isEmpty()
    var textInput by remember { mutableStateOf( taskData.task) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(
            text = "Hey, your stuff",
            fontFamily = AppConstants.getUbuntuFont()
        ) },
        text = {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = { Text(
                    "Type here...",
                    fontFamily = AppConstants.getUbuntuFont()
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 10,
                textStyle = TextStyle(fontFamily = AppConstants.getUbuntuFont())
            )
        },
        confirmButton = {
            TextButton(enabled = textInput.isNotEmpty(), onClick = {
                onConfirm(textInput)
                onDismiss()
            }) {
                Text(text = if (isForAdd) "Add" else "Update",
                    fontFamily = AppConstants.getUbuntuFont()
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = "Cancel",
                    fontFamily = AppConstants.getUbuntuFont()
                )
            }
        },
        modifier = Modifier.height(400.dp)
    )
}

@Preview
@Composable
fun PreviewCustomInputDialog() {
    var showDialog by remember { mutableStateOf(true) }

    NewTaskDialog(
        onDismiss = { showDialog = false },
        onConfirm = { inputText -> println("User input: $inputText") }
    )
}



