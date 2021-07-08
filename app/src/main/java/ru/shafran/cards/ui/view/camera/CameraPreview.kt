package ru.shafran.cards.ui.view.camera

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.guava.await
import ru.shafran.cards.ui.component.camera.rememberPreviewViewController
import ru.shafran.cards.utils.rememberLifecycleOwner


@Composable
fun CameraPreview(
    state: CameraState,
    modifier: Modifier = Modifier,
    onTokenDetected: (String) -> Unit = {},
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val camera by state.camera
    val isEnabled by state.isEnabled

    val cameraProvider = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = rememberPreviewView()
    val previewController = rememberPreviewViewController(previewView.surfaceProvider)

    LaunchedEffect(key1 = isEnabled, block = {
        if (isEnabled) {
            previewController.onEnable()
            Log.d("preview_controller", "onEnable")
        }else {
            previewController.onDisable()
            Log.d("preview_controller", "onDisable")
        }
    })
    LaunchedEffect(Unit) {
        val provider = cameraProvider.await()
        provider.tryBind(lifecycleOwner, camera, previewController.preview)
    }
    AndroidView(modifier = modifier, factory = { previewView })

}

@Composable
fun rememberPreviewView(): PreviewView {
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
) {
    try {
        unbindAll()
        bindToLifecycle(lifecycleOwner, selector, preview)
    } catch (e: Exception) {
        Log.e("CameraPreview", "Use case binding failed", e)
    }
}