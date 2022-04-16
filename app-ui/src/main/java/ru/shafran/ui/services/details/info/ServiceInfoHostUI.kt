package ru.shafran.ui.services.details.info

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.services.details.info.ServiceInfoHost
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI

@Composable
fun ServiceInfoHostUI(component: ServiceInfoHost, modifier: Modifier) {
    Children(routerState = component.routerState) {
        ServiceInfoNavHost(it.instance, modifier)
    }
}

@Composable
fun ServiceInfoNavHost(child: ServiceInfoHost.Child, modifier: Modifier) {
    when (child) {
        is ServiceInfoHost.Child.Loaded ->
            LoadedServiceInfoUI(component = child.component, modifier = modifier)
        is ServiceInfoHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
        is ServiceInfoHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
    }
}