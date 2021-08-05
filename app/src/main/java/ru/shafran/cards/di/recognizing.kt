package ru.shafran.cards.di

import androidx.camera.core.ImageAnalysis
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import org.koin.dsl.module
import ru.shafran.cards.analysis.CardTokenAnalyser
import ru.shafran.cards.analysis.MLKitCardTokenAnalyser

val recognizingModule = module {
    factory<BarcodeScannerOptions> {
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }
    single { BarcodeScanning.getClient(get()) }
    factory<CardTokenAnalyser> {
        MLKitCardTokenAnalyser(get())
    }
}