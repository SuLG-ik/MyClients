package ru.shafran.cards.ui.component.camera

import androidx.camera.core.ImageProxy
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.ui.component.details.CardDetails

interface Camera {

    val isEnabled: StateFlow<Boolean>

    val details: CardDetails

    fun onDisable()

    fun onEnable()

    fun processImage(proxy: ImageProxy)

}