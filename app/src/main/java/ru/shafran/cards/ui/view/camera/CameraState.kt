package ru.shafran.cards.ui.view.camera

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.*

interface CameraState {
    val isEnabled: State<Boolean>
    val camera: State<CameraSelector>

    fun setEnabled(state: Boolean)
    fun setCamera(camera: CameraSelector)
}

private class ProvidedCameraState(
    isEnabled: Boolean = true,
    camera: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
) : CameraState {
    override val isEnabled: MutableState<Boolean> = mutableStateOf(isEnabled)
    override val camera: MutableState<CameraSelector> = mutableStateOf(camera)

    override fun setEnabled(state: Boolean) {
        isEnabled.value = state
    }

    override fun setCamera(camera: CameraSelector) {
        this.camera.value = camera
    }

}

@Composable
fun rememberCameraState(
    isEnabled: Boolean = true,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
): CameraState {
    return remember { ProvidedCameraState(isEnabled, cameraSelector) }
}

