package ru.shafran.cards.ui.component.camera

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import ru.shafran.cards.ui.view.camera.CameraPreview
import ru.shafran.cards.ui.view.camera.rememberCameraState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraUI(component: Camera, modifier: Modifier = Modifier) {
    val state = rememberCameraState()

    val isEnabled by component.isEnabled.collectAsState()

    LaunchedEffect(key1 = isEnabled, block = {
        Log.d("CameraStateCheck", "state = $isEnabled")
        state.setEnabled(isEnabled)
    })
    val cameraState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    cameraState.shouldShowRationale
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
                state = state,
                onRecognizeImage = component::processImage,
                modifier = modifier)
        }
    )

}

@Composable
fun PermissionNotGranted(onRequest: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Камера необходима для чтения карт",
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(15.dp)
        )
        OutlinedButton(onClick = onRequest) {
            Text("Дать доступ", style = MaterialTheme.typography.h6)
        }
    }
}