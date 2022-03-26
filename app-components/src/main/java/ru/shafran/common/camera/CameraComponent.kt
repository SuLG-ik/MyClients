package ru.shafran.common.camera

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow

internal class CameraComponent(
    componentContext: ComponentContext,
    private val onDetected: (String) -> Unit,
    private val onOpenSettings: () -> Unit,
) : Camera, ComponentContext by componentContext {

    override val isEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)


    override fun onPause() {
        isEnabled.value = false
    }

    override fun onEnable() {
        isEnabled.value = true
    }


    override fun onDetected(cardToken: String) {
        onDetected.invoke(cardToken)
    }

    override fun onCameraPermissionRequest() {
        onOpenSettings.invoke()
    }



}