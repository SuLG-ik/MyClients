package ru.shafran.ui.services.details

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.common.services.details.host.ServicesDetailsHost
import ru.shafran.ui.services.details.create.ServiceConfigurationCreatingHostUI
import ru.shafran.ui.services.details.create.ServiceCreatingHostUI
import ru.shafran.ui.services.details.edit.ServiceEditingHostUI
import ru.shafran.ui.services.details.info.ServiceInfoHostUI
import ru.shafran.ui.view.ExtendedModalBottomSheet

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServicesDetailsHostUI(
    component: ServicesDetailsHost,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val routerState = component.routerState.subscribeAsState()
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden) {
        if (it == ModalBottomSheetValue.Hidden) {
            component.onHide()
        }
        return@rememberModalBottomSheetState true
    }
    LaunchedEffect(key1 = routerState.value, block = {
        try {
            if (routerState.value.activeChild.instance is ServicesDetailsHost.Child.Hidden) {
                state.animateTo(ModalBottomSheetValue.Hidden, tween(50))
            } else {
                state.animateTo(ModalBottomSheetValue.Expanded)
            }
        } catch (e: Exception) {
        }
    }
    )
    Box(modifier = modifier) {
        ExtendedModalBottomSheet(
            sheetState = state,
            sheetContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Children(routerState = routerState.value) {
                        ServicesDetailsHost(
                            child = it.instance,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                        )
                    }
                }
            },
            sheetElevation = 0.dp,
            modifier = Modifier.fillMaxWidth(),
            content = content,
        )
    }
}


@Composable
fun ServicesDetailsHost(child: ServicesDetailsHost.Child<Any?>, modifier: Modifier) {
    when (child) {
        is ServicesDetailsHost.Child.Hidden -> Text("")
        is ServicesDetailsHost.Child.ServiceInfo ->
            ServiceInfoHostUI(child.component, modifier)
        is ServicesDetailsHost.Child.CreateService ->
            ServiceCreatingHostUI(component = child.component, modifier = modifier)
        is ServicesDetailsHost.Child.CreateConfiguration ->
            ServiceConfigurationCreatingHostUI(component = child.component, modifier = modifier)
        is ServicesDetailsHost.Child.EditConfiguration -> TODO()
        is ServicesDetailsHost.Child.EditService ->
            ServiceEditingHostUI(component = child.component, modifier = modifier)
    }
}