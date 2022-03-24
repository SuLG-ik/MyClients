package ru.shafran.ui.view.camera

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun rememberImageRecognizer(
    onRecognize: (String) -> Unit = {},
): ImageRecognizer {
    val recognizer = remember { MLKitImageRecognizer(onRecognize) }

    DisposableEffect(key1 = recognizer, effect = {
        recognizer.enable()
        onDispose {
            recognizer.disable()
        }
    })

    return recognizer
}

sealed interface ImageRecognizer {

    val isEnabled: Boolean

    fun setEnabled(value: Boolean)

}

internal class MLKitImageRecognizer(
    private val onRecognize: suspend (String) -> Unit,
) : ImageRecognizer {

    private val mlKit = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )

    val analysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .apply { setAnalyzer(Dispatchers.IO.asExecutor(), ::analyze) }

    @SuppressLint("UnsafeOptInUsageError")
    private fun analyze(proxy: ImageProxy) {
        runBlocking {
            if (isEnabled)
                proxy.image?.let { it ->
                    val image =
                        InputImage.fromMediaImage(it, proxy.imageInfo.rotationDegrees)
                    val barcodes = mlKit.process(image).await()
                    produceBarcodes(barcodes)
                }
            proxy.close()
        }
    }

    private suspend fun produceBarcodes(barcodes: List<Barcode>) {
        val barcode = barcodes.firstOrNull() ?: return
        val barcodeValue = barcode.rawValue ?: return
        withContext(Dispatchers.Main) {
            onRecognize.invoke(barcodeValue)
        }
    }

    private val _isEnabled = mutableStateOf(false)
    override val isEnabled by _isEnabled


    override fun setEnabled(value: Boolean) {
        if (value) enable() else disable()
    }

    fun disable() {
        _isEnabled.value = false
    }

    fun enable() {
        _isEnabled.value = true
    }


}