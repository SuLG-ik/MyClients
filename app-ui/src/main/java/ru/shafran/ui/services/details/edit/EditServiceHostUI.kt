package ru.shafran.ui.services.details.edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.services.details.edit.ServiceEditingHost
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI

@Composable
fun ServiceEditingHostUI(
    component: ServiceEditingHost,
    modifier: Modifier,
) {
    Children(routerState = component.routerState) {
        ServiceEditingNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun ServiceEditingNavHost(child: ServiceEditingHost.Child, modifier: Modifier) {
    when (child) {
        is ServiceEditingHost.Child.EditService ->
            ServiceEditingUI(component = child.component, modifier = modifier)
        is ServiceEditingHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
        is ServiceEditingHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}