package com.codexdroid.officetoffice.presentation.ui.screens

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codexdroid.officetoffice.presentation.ui.MainActivity

@Composable
fun PermissionHandlerScreen(
    permissions: Array<String>,
    onPermissionsGranted: () -> Unit) {

    var allPermissionsGranted by remember { mutableStateOf(false) }
    var shouldShowRationale by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val settingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        checkPermissions(context, permissions) { granted ->
            if (granted) {
                allPermissionsGranted = true
                onPermissionsGranted()
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsResult ->
        val isAllGranted = permissionsResult.all { it.value }
        if (isAllGranted) {
            Log.d("PermissionStatus", "All permissions granted!")
            allPermissionsGranted = true
            onPermissionsGranted()
        } else {
            val shouldShowRationaleForAny = permissions.any { permission ->
                ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, permission)
            }
            shouldShowRationale = shouldShowRationaleForAny
        }
    }

    LaunchedEffect(Unit) {
        checkPermissions(context, permissions) { granted ->
            if (granted) {
                allPermissionsGranted = true
                onPermissionsGranted()
            } else {
                permissionLauncher.launch(permissions)
            }
        }
    }

    if (!allPermissionsGranted) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (shouldShowRationale) {
                Text(text = "We need these permissions to provide the app's core functionality.")
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(onClick = {
                permissionLauncher.launch(permissions)
            }) {
                Text(text = "Grant Permissions")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                settingsLauncher.launch(intent)
            }) {
                Text(text = "Open App Settings")
            }
        }
    }
}

/**
 * Check if all permissions are granted
 */
private fun checkPermissions(context: Context, permissions: Array<String>, onResult: (Boolean) -> Unit) {
    val allPermissionsGranted = permissions.all { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
    onResult(allPermissionsGranted)
}