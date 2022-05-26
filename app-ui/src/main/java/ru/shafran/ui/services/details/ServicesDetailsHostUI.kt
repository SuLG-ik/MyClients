package ru.shafran.ui.services.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shafran.common.services.details.host.ServicesDetailsHost
import ru.shafran.ui.services.details.create.ServiceConfigurationCreatingHostUI
import ru.shafran.ui.services.details.create.ServiceCreatingHostUI
import ru.shafran.ui.services.details.edit.ServiceEditingHostUI
import ru.shafran.ui.services.details.info.ServiceInfoHostUI
import ru.shafran.ui.view.BottomSheetComponentUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServicesDetailsHostUI(
    component: ServicesDetailsHost,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BottomSheetComponentUI(
        router = component.routerState,
        isHidden = { it is ServicesDetailsHost.Child.Hidden },
        onHide = component.onHide,
        modifier = modifier,
        navHost = {
            ServicesDetailsHost(
                child = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
            )
        },
        content = content,
    )
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