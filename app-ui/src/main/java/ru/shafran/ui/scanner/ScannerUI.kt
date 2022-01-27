package ru.shafran.ui.scanner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.shafran.common.scanner.CustomerScanner
import ru.shafran.ui.camera.CameraUI
import ru.shafran.ui.details.CustomerDetailsHostUI

@Composable
fun CustomerScannerUI(component: CustomerScanner, modifier: Modifier) {
    CustomerDetailsHostUI(
        component = component.customerDetails,
        modifier = modifier,
    ) {
        CameraUI(
            component = component.camera,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
