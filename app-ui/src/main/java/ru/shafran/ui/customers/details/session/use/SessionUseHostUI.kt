package ru.shafran.ui.customers.details.session.use

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.customers.details.sessions.use.SessionUseHost

@Composable
fun SessionUseHostUI(component: SessionUseHost, modifier: Modifier) {
    Children(routerState = component.routerState) {
        SessionUseNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun SessionUseNavHost(child: SessionUseHost.Child, modifier: Modifier) {
    when (child) {
        is SessionUseHost.Child.Loading ->
            SessionUsePlaceholderUI(component = child.component, modifier = modifier)
        is SessionUseHost.Child.Loaded ->
            SessionUseUI(component = child.component, modifier = modifier)
    }
}
