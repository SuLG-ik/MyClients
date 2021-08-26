package ru.shafran.cards.ui.component.camera

import androidx.camera.core.ImageProxy
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.DetectedCard

interface Camera {

    val isDetailShown: StateFlow<Boolean>

    val isEnabled: StateFlow<Boolean>

    fun onDisable()

    fun onEnable()

    fun processImage(proxy: ImageProxy)

    fun onDetected(card: DetectedCard)


}