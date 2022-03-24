package ru.shafran.ui.camera

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.collectLatest
import ru.shafran.common.camera.Camera
import ru.shafran.ui.R
import ru.shafran.ui.view.camera.CameraPreview
import ru.shafran.ui.view.camera.rememberCameraState
import ru.shafran.ui.view.camera.rememberImageRecognizer

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun CameraUI(component: Camera, modifier: Modifier = Modifier) {
    val state = rememberCameraState()
    val recognizer = rememberImageRecognizer(component::onDetected)

    LaunchedEffect(Unit) {
        component.isEnabled.collectLatest {
            state.setEnabled(it)
            recognizer.setEnabled(it)
        }
    }

    val cameraState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    PermissionRequired(
        permissionState = cameraState,
        permissionNotGrantedContent = {
            PermissionNotGranted(
                onRequest = cameraState::launchPermissionRequest,
                modifier = modifier,
            )
        },
        permissionNotAvailableContent = {
            PermissionNotGranted(
                onRequest = component::onCameraPermissionRequest,
                modifier = modifier,
            )
        },
        content = {
            CameraPreview(
                cameraState = state,
                recognizer = recognizer,
                modifier = modifier,
            )
        }
    )
}

@Composable
internal fun PermissionNotGranted(onRequest: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(R.string.scanner_camera_permanent_deny),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(15.dp)
        )
        OutlinedButton(onClick = onRequest) {
            Text(
                stringResource(R.string.scanner_camera_accept),
            )
        }
    }
}