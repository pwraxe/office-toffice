package com.codexdroid.officetoffice.presentation.ui.comp_funs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codexdroid.officetoffice.utils.AppConstants
import kotlinx.coroutines.launch


@Preview
@Composable
fun OnBoardPreview(modifier: Modifier = Modifier) {
    OnBoardingDialog(onUnderstoodClicked = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingDialog(
    onUnderstoodClicked: () -> Unit,
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()


    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onUnderstoodClicked()
            }
        },
        sheetState = sheetState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            InfoCard("Check-In", " helps to get record Morning Time")
            InfoCard("Check-Out", " helps to get record Evening Time")
            InfoCard("OfficeToffice Header"," helps to reset the time")
            InfoCard("DEVICE VOLUME DOWN",  " helps to add new task")
            InfoCard("DEVICE VOLUME UP", " helps to Review this App info again")


            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        onUnderstoodClicked()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Understood", fontFamily = AppConstants.getUbuntuFont())
            }
        }
    }
}



@Composable
fun InfoCard(
    start: String, end: String,
    modifier: Modifier = Modifier) {
    OutlinedCard (
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = AppConstants.getUbuntuFont(true),
                    )
                ) {
                    append(start)
                }
                append(end)
            },
            fontFamily = AppConstants.getUbuntuFont(),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
        )
    }
}