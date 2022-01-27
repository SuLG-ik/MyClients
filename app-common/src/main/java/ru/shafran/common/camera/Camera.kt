package ru.shafran.common.camera

import kotlinx.coroutines.flow.StateFlow

interface Camera {

    val isEnabled: StateFlow<Boolean>

    fun onPause()

    fun onEnable()

    fun onDetected(cardToken: String)

    fun onCameraPermissionRequest()

}