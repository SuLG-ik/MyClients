package ru.shafran.ui.view.camera

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.*

interface CameraState {
    val isEnabled: Boolean
    val camera: CameraSelector

    fun setEnabled(state: Boolean)
    fun setCamera(camera: CameraSelector)
}

private class ProvidedCameraState(
    isEnabled: Boolean = true,
    camera: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
) : CameraState {
    private val _isEnabled: MutableState<Boolean> = mutableStateOf(isEnabled)
    private val _camera: MutableState<CameraSelector> = mutableStateOf(camera)

    override val isEnabled: Boolean by _isEnabled
    override val camera: CameraSelector by _camera

    override fun setEnabled(state: Boolean) {
        _isEnabled.value = state
    }

    override fun setCamera(camera: CameraSelector) {
        _camera.value = camera
    }

}

@Composable
fun rememberCameraState(
    isEnabled: Boolean = true,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
): CameraState {
    return remember(isEnabled, cameraSelector) { ProvidedCameraState(isEnabled, cameraSelector) }
}

