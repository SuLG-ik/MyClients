package ru.shafran.cards.ui.component.camera

import androidx.camera.core.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberPreviewViewController(
    surfaceProvider: Preview.SurfaceProvider,
    preview: Preview = Preview.Builder().build(),
): PausedPreviewController {
    return remember { PausedPreviewController(surfaceProvider, preview) }
}

class PausedPreviewController(
    private val surfaceProvider: Preview.SurfaceProvider,
    val preview: Preview = Preview.Builder().build(),
) {

    fun onEnable() {
        preview.setSurfaceProvider(surfaceProvider)
    }

    fun onDisable() {
        preview.setSurfaceProvider(null)
    }

}
