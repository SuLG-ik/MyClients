package ru.shafran.ui.details.edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.details.edit.CustomerEditingHost
import ru.shafran.ui.loading.LoadingUI

@Composable
fun CustomerEditingHostUI(component: CustomerEditingHost, modifier: Modifier) {
    Children(routerState = component.routerState) {
        CustomerEditingNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun CustomerEditingNavHost(child: CustomerEditingHost.Child, modifier: Modifier) {
    when(child) {
        is CustomerEditingHost.Child.Activating ->
            CustomerActivationUI(component = child.component, modifier = modifier)
        is CustomerEditingHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}
