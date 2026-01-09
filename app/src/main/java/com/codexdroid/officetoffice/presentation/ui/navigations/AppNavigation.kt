package com.codexdroid.officetoffice.presentation.ui.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codexdroid.officetoffice.presentation.ui.screens.CheckInOutScreen
import com.codexdroid.officetoffice.presentation.ui.screens.NotificationScreen
import com.codexdroid.officetoffice.presentation.viewmodels.TaskViewModel

@Composable
fun AppNavigation(taskViewModel: TaskViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.CheckInOutScreen.route) {
        composable(Screens.CheckInOutScreen.route) {
            CheckInOutScreen(taskViewModel, navHostController = navController, modifier = Modifier)
        }
        composable(Screens.NotificationScreen.route) {
             NotificationScreen(navController)
        }
    }
}