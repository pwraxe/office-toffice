package com.codexdroid.officetoffice.presentation.ui.comp_funs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
fun OnBoardPreview() {
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
        sheetState = sheetState,
        windowInsets = WindowInsets(0,0,0,0)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            listOf(
                "Check-In" to "Records your morning check-in time to start tracking work hours.",
                "Check-Out" to "Records your evening check-out time to calculate total working hours.",
                "Office to Office" to "Resets both check-in and check-out times for a new work session.",
                "Device Volume Down" to "Quickly add a new task using the hardware volume down button.",
                "Device Volume Up" to "Reopen and review the application information at any time.",
                "" to "The app uses a 24-hour time format to calculate total working hours. If the calculated duration is negative or invalid, the total hours will be hidden."
            ).forEach {
                InfoCard(it.first,it.second)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        onUnderstoodClicked()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Okay, I got it", fontFamily = AppConstants.getUbuntuFont())
            }
        }
    }
}

@Composable
fun InfoCard(
    spanText: String, end: String
) {
    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = AppConstants.getUbuntuFont(true),
                    )
                ) {
                    append(spanText)
                }
                if (spanText != "") append(" ")
                append(end)
            },
            fontFamily = AppConstants.getUbuntuFont(),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
        )
    }
}