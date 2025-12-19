package com.codexdroid.officetoffice.utils

import android.content.Context
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.codexdroid.officetoffice.R
import java.lang.reflect.Modifier
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object AppConstants {

    fun getUbuntuFont(isBold: Boolean = false): FontFamily  {
        val font = if(isBold) R.font.ubuntu_bold else R.font.ubuntu_regular
        return FontFamily(Font(font))
    }

    @Composable
    fun SmallText(text: String) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontFamily = getUbuntuFont()
        )
    }


    @Composable
    fun RegularText(text: String) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = getUbuntuFont()
        )
    }

    fun Long.to12HourFormat(): String {
        if (this == 0L) return "00:00"
        val sdf = SimpleDateFormat("hh:mm", Locale.getDefault())
        return sdf.format(Date(this))
    }

    fun getVibratorService(context: Context): Vibrator {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun formatTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata") // Set IST TimeZone
        return sdf.format(Date(timestamp))
    }

}