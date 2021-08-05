package ru.shafran.cards.analysis

import androidx.camera.core.ImageProxy
import kotlinx.coroutines.flow.StateFlow

interface CardTokenAnalyser {
    val tokens: StateFlow<String>
    fun pause()
    fun resume()
    fun process(proxy: ImageProxy, onDetected: (String) -> Unit)
}