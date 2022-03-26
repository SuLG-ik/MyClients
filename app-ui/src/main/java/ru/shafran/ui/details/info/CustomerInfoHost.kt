package ru.shafran.ui.details.info

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.details.info.CustomerInfoHost
import ru.shafran.ui.error.ErrorUI

@Composable
fun CustomerInfoHostUI(component: CustomerInfoHost, modifier: Modifier) {
    Children(routerState = component.routerState) {
        CustomerInfoNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun CustomerInfoNavHost(child: CustomerInfoHost.Child, modifier: Modifier) {
    when (child) {
        is CustomerInfoHost.Child.Loading ->
            LoadingCustomerDetailsUI(component = child.component, modifier = modifier)
        is CustomerInfoHost.Child.Inactivated ->
            InactivatedCustomerDetailsUI(component = child.component, modifier = modifier)
        is CustomerInfoHost.Child.Preloaded ->
            PreloadedCustomerDetailsUI(component = child.component, modifier = modifier)
        is CustomerInfoHost.Child.Loaded ->
            LoadedCustomerDetailsUI(component = child.component, modifier = modifier)
        is CustomerInfoHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
    }
}
