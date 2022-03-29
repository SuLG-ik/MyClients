package ru.shafran.ui.customers.details.session.activation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.customers.details.sessions.activation.SessionActivationHost

@Composable
fun SessionActivationHostUI(component: SessionActivationHost, modifier: Modifier) {
    Children(routerState = component.routerState) {
        SessionActivationNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun SessionActivationNavHost(child: SessionActivationHost.Child, modifier: Modifier) {
    when (child) {
        is SessionActivationHost.Child.Loading ->
            SessionActivationPlaceholderUI(component = child.component, modifier = modifier)
        is SessionActivationHost.Child.Loaded ->
            SessionActivationUI(component = child.component, modifier = modifier)
    }
}
