package ru.shafran.ui.customers.details.session.deactivation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.customers.details.sessions.deactivation.SessionDeactivationHost
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI

@Composable
fun SessionDeactivationHostUI(component: SessionDeactivationHost, modifier: Modifier) {
    Children(routerState = component.routerState) {
        SessionDeactivationNavHost(it.instance, modifier)
    }
}


@Composable
fun SessionDeactivationNavHost(
    child: SessionDeactivationHost.Child,
    modifier: Modifier,
) {
    when (child) {
        is SessionDeactivationHost.Child.DeactivateSession ->
            SessionDeactivatingUI(component = child.component, modifier = modifier)
        is SessionDeactivationHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
        is SessionDeactivationHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}