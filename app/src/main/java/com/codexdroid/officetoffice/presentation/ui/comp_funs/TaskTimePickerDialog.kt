package com.codexdroid.officetoffice.presentation.ui.comp_funs

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.codexdroid.officetoffice.utils.AppConstants.getUbuntuFont
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTimePickerDialog(
    timeFor: String,
    onDismissRequest: () -> Unit,
    onTimeSelected: (Long) -> Unit
) {
    val calendar = Calendar.getInstance()

    val selectedHour by remember { mutableIntStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    val selectedMinute by remember { mutableIntStateOf(calendar.get(Calendar.MINUTE)) }

    val timePicker = rememberTimePickerState(initialHour = selectedHour, initialMinute = selectedMinute, is24Hour = false)

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(
            text = if (timeFor == "AM") "Select Check-In Time" else "Select Check-Out Time",
            fontFamily = getUbuntuFont(true)
        )
        },
        text = {
            TimePicker(
                state = timePicker,
                modifier = Modifier.padding(10.dp),
                layoutType = TimePickerLayoutType.Vertical,
            )
        },
        confirmButton = {
            Button(onClick = {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                calendar.set(Calendar.MINUTE, timePicker.minute)
                onTimeSelected(calendar.timeInMillis) }) {
                Text(
                    text = "SELECT",
                    fontFamily = getUbuntuFont()
                )
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = "Cancel",
                    fontFamily = getUbuntuFont()
                )
            }
        }
    )
}
