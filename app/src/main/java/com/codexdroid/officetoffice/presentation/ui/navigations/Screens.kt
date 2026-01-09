package com.codexdroid.officetoffice.presentation.ui.navigations

sealed class Screens(val route: String) {
    data object CheckInOutScreen : Screens("check_in_out_screen")
    data object NotificationScreen : Screens("notification_screen")
}