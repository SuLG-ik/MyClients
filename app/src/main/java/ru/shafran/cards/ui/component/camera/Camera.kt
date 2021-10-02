package ru.shafran.cards.ui.component.camera

import androidx.camera.core.ImageProxy
import kotlinx.coroutines.flow.StateFlow

interface Camera {

    val isEnabled: StateFlow<Boolean>

    fun onPause()

    fun onEnable()

    fun processImage(proxy: ImageProxy)

    fun onDetected(cardToken: String)

    fun onCameraPermissionRequest()

}