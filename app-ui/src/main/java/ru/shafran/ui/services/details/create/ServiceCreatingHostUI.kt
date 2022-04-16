package ru.shafran.ui.services.details.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.services.details.create.ServiceCreatingHost
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI

@Composable
fun ServiceCreatingHostUI(
    component: ServiceCreatingHost,
    modifier: Modifier,
) {
    Children(routerState = component.routerState) {
        CreateServiceNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun CreateServiceNavHost(child: ServiceCreatingHost.Child, modifier: Modifier) {
    when (child) {
        is ServiceCreatingHost.Child.CreateService ->
            ServiceCreatingUI(component = child.component, modifier = modifier)
        is ServiceCreatingHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
        is ServiceCreatingHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}