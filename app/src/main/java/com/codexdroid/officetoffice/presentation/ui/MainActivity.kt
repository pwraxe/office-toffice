package com.codexdroid.officetoffice.presentation.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.codexdroid.officetoffice.presentation.ui.navigations.AppNavigation
import com.codexdroid.officetoffice.presentation.ui.screens.PermissionHandlerScreen
import com.codexdroid.officetoffice.presentation.theme.OfficeTofficeTheme
import com.codexdroid.officetoffice.presentation.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var requiredPermission  = mutableListOf<String>()
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requiredPermission.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            taskViewModel = hiltViewModel()
            OfficeTofficeTheme {
                var isAllPermissionGranted by remember { mutableStateOf(false) }

                if (isAllPermissionGranted) {
                    AppNavigation(taskViewModel)
                } else {
                    PermissionHandlerScreen(
                        permissions = requiredPermission.toTypedArray(),
                        onPermissionsGranted = {
                            isAllPermissionGranted = true
                        }
                    )
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            taskViewModel.updateOpenNewTaskDialogToEdit(isOpen = true, forEdit = false)
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            taskViewModel.makeOnboardingUnDone()
        }
        return true
    }
}

