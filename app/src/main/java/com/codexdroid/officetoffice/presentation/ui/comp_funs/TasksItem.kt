package com.codexdroid.officetoffice.presentation.ui.comp_funs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codexdroid.officetoffice.R
import com.codexdroid.officetoffice.data.model.TaskData
import com.codexdroid.officetoffice.utils.AppConstants

@Preview
@Composable
fun TaskItemPreview(modifier: Modifier = Modifier) {
    TasksItem(
        TaskData(
            0,
            "Perform Click Event on Monday and frelease this is quality time of developers who working on very diffivuyl levbal nd hence so this would be nice effotedd",
            System.currentTimeMillis(),
            isDone = true
        ), {}, {}, {}, modifier
    )
}

@Composable
fun TasksItem(
    taskData: TaskData,
    onCheckClicked: (TaskData) -> Unit,
    onEditClicked: (TaskData) -> Unit,
    onDeleteClicked:(TaskData) -> Unit,
    modifier: Modifier = Modifier) {
    OutlinedCard(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp),

        colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Column {
            Row {
                Card (
                    modifier = modifier.padding(10.dp),
                    shape = RoundedCornerShape(6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = AppConstants.formatTime(taskData.createdOn),
                        fontFamily = AppConstants.getUbuntuFont(true),
                        fontSize = 16.sp,
                        modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = modifier.weight(1f))

                IconButton(onClick = { onEditClicked(taskData) }, enabled = !taskData.isDone) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit_note),
                        contentDescription = "Edit note")
                }

                IconButton(onClick = { onDeleteClicked(taskData) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = "Delete note")
                }
            }
            Row(
                modifier = modifier
                    .clickable { onCheckClicked(taskData) }
                    .padding(horizontal = 10.dp, vertical = 10.dp)) {

                Icon(
                    painter = painterResource(if (taskData.isDone) R.drawable.ic_checked else R.drawable.ic_uncheck),
                    contentDescription = "Edit note",
                    modifier = modifier,
                    tint = if (taskData.isDone) Color(0xFF75DB42) else Color(0xFFFF0000)
                )

                Text(
                    text = taskData.task,
                    fontFamily = AppConstants.getUbuntuFont(),
                    fontSize = 12.sp,
                    modifier = modifier
                        .padding(start = 6.dp)
                        .align(alignment = Alignment.CenterVertically),
                    textDecoration = if (taskData.isDone) TextDecoration.LineThrough else TextDecoration.None
                )
            }
        }
    }
}