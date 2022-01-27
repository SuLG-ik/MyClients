package ru.shafran.ui.view.camera

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.guava.await


@Composable
internal fun CameraPreview(
    cameraState: CameraState,
    modifier: Modifier = Modifier,
    recognizer: ImageRecognizer? = null,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val cameraProvider = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = rememberPreviewView()
    val previewController = rememberPreviewViewController(previewView.surfaceProvider)

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            cameraState.setEnabled(false)
            val provider = cameraProvider.get()
            previewController.onDisable()
            provider?.unbindAll()
        }
    })

    LaunchedEffect(cameraState.camera, cameraState.isEnabled) {
        val provider = cameraProvider.await()
        if (cameraState.isEnabled) {
            previewController.onEnable()
            provider.tryBind(
                lifecycleOwner,
                cameraState.camera,
                previewController.preview,
                (recognizer as? MLKitImageRecognizer?)?.analysis
            )
        } else {
            previewController.onDisable()
            provider.unbindAll()
        }
    }
    AndroidView(modifier = modifier, factory = { previewView })
}

@Composable
private fun rememberPreviewViewController(
    surfaceProvider: Preview.SurfaceProvider,
    preview: Preview = Preview.Builder().build(),
): PausedPreviewController {
    return remember { PausedPreviewController(surfaceProvider, preview) }
}

private class PausedPreviewController(
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


@Composable
private fun rememberPreviewView(): PreviewView {
    val context = LocalContext.current
    return remember {
        PreviewView(context).apply {
            this.scaleType = scaleType
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
    }
}

private fun ProcessCameraProvider.tryBind(
    lifecycleOwner: LifecycleOwner,
    selector: CameraSelector,
    preview: Preview,
    analysis: ImageAnalysis?,
) {
    try {
        unbindAll()
        if(analysis != null) {
            bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
                analysis,
            )
        } else {
            bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
            )
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}