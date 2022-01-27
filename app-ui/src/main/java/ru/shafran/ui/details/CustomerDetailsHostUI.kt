package ru.shafran.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.common.details.host.CustomerDetailsHost
import ru.shafran.ui.details.edit.CustomerEditingHostUI
import ru.shafran.ui.details.info.CustomerInfoHostUI
import ru.shafran.ui.details.sessions.SessionActivationHostUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CustomerDetailsHostUI(
    component: CustomerDetailsHost,
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
        if (routerState.value.activeChild.instance is CustomerDetailsHost.Child.Hidden) {
            state.hide()
        } else {
            state.show()
        }
    })
    Box(modifier = modifier) {
        ModalBottomSheetLayout(
            sheetState = state,
            sheetContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Children(routerState = routerState.value) {
                        CustomerDetailsNavHost(
                            child = it.instance,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                        )
                    }
                }
            },
            sheetElevation = 0.dp,
            sheetShape = MaterialTheme.shapes.large
                .copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp)),
            modifier = Modifier.fillMaxWidth(),
            content = content,
        )
    }
}


@Composable
private fun CustomerDetailsNavHost(
    child: CustomerDetailsHost.Child,
    modifier: Modifier,
) {
    when (child) {
        is CustomerDetailsHost.Child.Hidden ->
            Text("")
        is CustomerDetailsHost.Child.CustomerInfo ->
            CustomerInfoHostUI(component = child.component, modifier = modifier)
        is CustomerDetailsHost.Child.EditCustomer ->
            CustomerEditingHostUI(component = child.component, modifier = modifier)
        is CustomerDetailsHost.Child.SessionActivation ->
            SessionActivationHostUI(component = child.component, modifier = modifier)
    }
}