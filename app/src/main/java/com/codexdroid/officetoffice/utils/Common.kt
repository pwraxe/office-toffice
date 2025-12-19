package com.codexdroid.officetoffice.utils


import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.codexdroid.officetoffice.utils.AppConstants.getUbuntuFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.onClickListener(
    onClick: () -> Unit = {},
    onLongClick: () -> Unit
): Modifier {

    val context = LocalContext.current
    val vibrator = AppConstants.getVibratorService(context)

    Modifier.combinedClickable(enabled = true,
        onLongClick = {},
        onClick = {}
    )

    return this.pointerInput(Unit) {

        detectTapGestures(
            onPress = {
                onClick()
            },
            onLongPress = {
                onLongClick()
            }
        )
    }
}



@Composable
fun LargeText(text: String, modifier: Modifier =  Modifier) {
    Text(
        text = text,
        fontSize = 40.sp,
        fontFamily = getUbuntuFont(true),
        modifier = modifier
    )
}