package ru.shafran.ui.view.camera

import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.util.concurrent.atomic.AtomicBoolean


@Composable
internal fun CameraPreview(
    cameraState: CameraState,
    modifier: Modifier = Modifier,
    recognizer: ImageRecognizer? = null,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val previewView = rememberPreviewView()
    val previewController = rememberPreviewViewController(previewView.surfaceProvider)

    val cameraProvider = remember<CameraProvider> {
        CameraProviderImpl(
            context = context,
            lifecycleOwner = lifecycleOwner,
            cameraSelector = cameraState.camera,
            recognizer = recognizer,
            previewController = previewController,
        )
    }

    DisposableEffect(key1 = cameraState.isEnabled, effect = {
        if (cameraState.isEnabled)
            cameraProvider.enable()
        else
            cameraProvider.disable()
        onDispose {
            cameraProvider.disable()
        }
    })
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


private class CameraProviderImpl(
    context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val cameraSelector: CameraSelector,
    private val recognizer: ImageRecognizer?,
    private val previewController: PausedPreviewController,
) : CameraProvider {

    private val enable = AtomicBoolean(false)

    private val provider = ProcessCameraProvider.getInstance(context)

    override fun enable() {
        enable.set(true)
        enableIfNecessary()
        recognizer?.setEnabled(true)
        previewController.onEnable()
    }

    private fun enableIfNecessary() {
        if (!provider.isDone) {
            registerEnabling()
            return
        }
        enableProvider()
    }

    private fun registerEnabling() {
        provider.addListener({
            enableProvider()
        }, Dispatchers.Main.asExecutor())
    }

    private fun enableProvider() {
        if (enable.get()) {
            val camera = provider.get()
            camera.tryBind(
                lifecycleOwner,
                cameraSelector,
                previewController.preview,
                (recognizer as? MLKitImageRecognizer)?.analysis,
            )
        }
    }

    private fun unableProvider() {
        if (!enable.get()) {
            val camera = provider.get()
            camera.unbindAll()
        }
    }


    override fun disable() {
        enable.set(false)
        recognizer?.setEnabled(false)
        previewController.onDisable()
        if (provider.isDone && !provider.isCancelled) {
            unableProvider()
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
            if (analysis != null) {
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

}