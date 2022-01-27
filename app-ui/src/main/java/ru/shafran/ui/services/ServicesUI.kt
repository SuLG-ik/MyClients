package ru.shafran.ui.services

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.services.Services

@Composable
internal fun ServicesUI(component: Services, modifier: Modifier) {
    Children(routerState = component.routerState, modifier = modifier) {
        ServicesNavHost(child = it.instance, modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun ServicesNavHost(child: Services.Child, modifier: Modifier) {
    when (child) {
        is Services.Child.ServicesList ->
            ServicesListHostUI(component = child.component, modifier = modifier)
    }
}
