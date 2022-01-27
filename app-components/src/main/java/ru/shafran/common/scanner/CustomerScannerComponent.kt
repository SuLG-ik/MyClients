package ru.shafran.common.scanner

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.observe
import ru.shafran.common.camera.Camera
import ru.shafran.common.camera.CameraComponent
import ru.shafran.common.details.host.CustomerDetailsHost
import ru.shafran.common.details.host.CustomerDetailsHostComponent

internal class CustomerScannerComponent(
    componentContext: ComponentContext,
    onOpenSettings: () -> Unit,
) : CustomerScanner, ComponentContext by componentContext {

    override val customerDetails: CustomerDetailsHost =
        CustomerDetailsHostComponent(childContext("customer_details"))

    init {
        customerDetails.isShown.observe(lifecycle) { isShown ->
            if (isShown)
                camera.onPause()
            else
                camera.onEnable()
        }
    }

    override val camera: Camera = CameraComponent(
        componentContext = childContext("scanner_camera"),
        onDetected = this::onTokenDetected,
        onOpenSettings = onOpenSettings,
    )

    private fun onTokenDetected(token: String) {
        if (!customerDetails.isShown.value) {
            customerDetails.onShowCustomer(token)
        }
    }

}