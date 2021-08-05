package ru.shafran.cards.ui.view.camera

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.guava.await
import ru.shafran.cards.ui.component.camera.rememberPreviewViewController


@Composable
fun CameraPreview(
    state: CameraState,
    modifier: Modifier = Modifier,
    onRecognizeImage: ((ImageProxy) -> Unit)? = null,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current


    val cameraProvider = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = rememberPreviewView()
    val previewController = rememberPreviewViewController(previewView.surfaceProvider)

    LaunchedEffect(key1 = state.isEnabled, block = {
        if (state.isEnabled) {
            previewController.onEnable()
            Log.d("preview_controller", "onEnable")
        } else {
            previewController.onDisable()
            Log.d("preview_controller", "onDisable")
        }
    })
    LaunchedEffect(state.camera) {
        val provider = cameraProvider.await()
        provider.tryBind(lifecycleOwner, state.camera, previewController.preview, onRecognizeImage = onRecognizeImage)
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
    onRecognizeImage: ((ImageProxy) -> Unit)? = null,
) {
    val analysis =
        ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    if (onRecognizeImage != null) {
        analysis.setAnalyzer(Dispatchers.IO.asExecutor(), onRecognizeImage)
    }
    try {
        unbindAll()
        bindToLifecycle(
            lifecycleOwner,
            selector,
            preview,
            analysis,
        )
    } catch (e: Exception) {
        Log.e("CameraPreview", "Use case binding failed", e)
    }
}