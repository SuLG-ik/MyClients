package ru.shafran.cards.analysis

import android.annotation.SuppressLint
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow

class MLKitCardTokenAnalyser(private val barcodeScanner: BarcodeScanner) : CardTokenAnalyser {

    override val tokens: MutableStateFlow<String> = MutableStateFlow("")

    var isRunning = true
    override fun pause() {
        isRunning = false
    }

    override fun resume() {
        isRunning = true
    }

    override fun process(proxy: ImageProxy, onDetected: (String) -> Unit) {
        if (!isRunning) {
            proxy.close()
            return
        }
        val image = proxy.toImage()
        if (image == null) {
            proxy.close()
            return
        }

        barcodeScanner.process(image).addOnSuccessListener { barcodes ->
            if (barcodes.isEmpty()) {
                onDetected("")
            }
            for (barcode in barcodes) {
                barcode.rawValue?.let {
                    onDetected(it)
                }
            }
        }.addOnCompleteListener {
            proxy.close()
        }
    }
}


private fun ImageProxy.toImage(): InputImage? {
    @SuppressLint("UnsafeOptInUsageError")
    val image = image?.let {
        InputImage.fromMediaImage(it, imageInfo.rotationDegrees)
    }
    return image
}