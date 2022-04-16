package ru.shafran.ui.services.details.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.services.details.create.ServiceConfigurationCreatingHost
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI

@Composable
fun ServiceConfigurationCreatingHostUI(
    component: ServiceConfigurationCreatingHost,
    modifier: Modifier,
) {
    Children(routerState = component.routerState) {
        ServiceConfigurationNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun ServiceConfigurationNavHost(child: ServiceConfigurationCreatingHost.Child, modifier: Modifier) {
    when (child) {
        is ServiceConfigurationCreatingHost.Child.CreateConfiguration ->
            ServiceConfigurationCreatingUI(component = child.component, modifier = modifier)
        is ServiceConfigurationCreatingHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
        is ServiceConfigurationCreatingHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}