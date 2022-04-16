package ru.shafran.ui.customers.details.session.use

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.customers.details.sessions.use.SessionUsageHost

@Composable
fun SessionUseHostUI(component: SessionUsageHost, modifier: Modifier) {
    Children(routerState = component.routerState) {
        SessionUseNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun SessionUseNavHost(child: SessionUsageHost.Child, modifier: Modifier) {
    when (child) {
        is SessionUsageHost.Child.Loading ->
            SessionUsePlaceholderUI(component = child.component, modifier = modifier)
        is SessionUsageHost.Child.Loaded ->
            SessionUseUI(component = child.component, modifier = modifier)
    }
}
