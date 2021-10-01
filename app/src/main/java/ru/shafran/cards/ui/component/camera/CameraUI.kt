package ru.shafran.cards.ui.component.camera

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.shafran.cards.ui.view.camera.CameraPreview
import ru.shafran.cards.ui.view.camera.rememberCameraState

@Composable
fun CameraUI(component: Camera, modifier: Modifier = Modifier) {
    val state = rememberCameraState()

    val isEnabled by component.isEnabled.collectAsState()

    LaunchedEffect(key1 = isEnabled, block = {
        Log.d("CameraStateCheck", "state = $isEnabled")
        state.setEnabled(isEnabled)
    })
    CameraPreview(
        state = state,
        onRecognizeImage = component::processImage,
        modifier = modifier)
}