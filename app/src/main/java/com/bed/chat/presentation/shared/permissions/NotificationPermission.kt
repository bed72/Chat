package com.bed.chat.presentation.shared.permissions

import android.os.Build

import androidx.core.app.ActivityCompat
import androidx.activity.compose.LocalActivity

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog

import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult

import com.bed.chat.R

@Composable
fun NotificationPermission(
    modifier: Modifier = Modifier,
    onDenied: () -> Unit = {},
    onGranted: () -> Unit = {}
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

    val activity = LocalActivity.current
    val (shouldInformation, setShouldInformation) = remember { mutableStateOf(false) }
    val (requestPermission, setRequestPermission) = rememberSaveable { mutableStateOf(false) }

    val permission = android.Manifest.permission.POST_NOTIFICATIONS
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        setRequestPermission(true)

        if (granted) onGranted()
        else {
            val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity!!, permission
            )

            if (shouldShowRationale) setShouldInformation(true)
            else onDenied()
        }
    }

    LaunchedEffect(Unit) {
        if (!requestPermission) launcher.launch(permission)
    }

    if (shouldInformation) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {},
            title = {
                Text(stringResource(R.string.notification_title))
            },
            text = {
                Text(stringResource(R.string.notification_description))
            },
            confirmButton = {
                TextButton(onClick = {
                    setShouldInformation(false)
                }) {
                    Text(stringResource(R.string.notification_granted))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    setShouldInformation(false)
                }) {
                    Text(stringResource(R.string.notification_denied))
                }
            }
        )
    }
}
