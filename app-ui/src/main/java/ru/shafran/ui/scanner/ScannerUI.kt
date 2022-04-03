package ru.shafran.ui.scanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.common.scanner.CustomerScanner
import ru.shafran.ui.R
import ru.shafran.ui.camera.CameraUI
import ru.shafran.ui.customers.details.CustomerDetailsHostUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScannerUI(component: CustomerScanner, modifier: Modifier) {
    CustomerDetailsHostUI(
        component = component.customerDetails,
        modifier = modifier,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    FloatingActionButton(onClick = component::onSearch) {
                        Icon(painterResource(id = R.drawable.ic_search), contentDescription = null)
                    }
                    FloatingActionButton(onClick = component::onGenerateCustomer) {
                        Icon(painterResource(id = R.drawable.add_customer),
                            contentDescription = null)
                    }
                }
            }
        ) {
            CameraUI(
                component = component.camera,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
