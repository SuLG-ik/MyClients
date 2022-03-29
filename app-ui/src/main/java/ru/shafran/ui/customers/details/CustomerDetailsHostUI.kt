package ru.shafran.ui.customers.details

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
import io.github.aakira.napier.Napier
import ru.shafran.common.customers.details.host.CustomerDetailsHost
import ru.shafran.ui.customers.details.edit.CustomerEditingHostUI
import ru.shafran.ui.customers.details.generator.CardGeneratorHostUI
import ru.shafran.ui.customers.details.generator.CustomerSenderUI
import ru.shafran.ui.customers.details.info.CustomerInfoHostUI
import ru.shafran.ui.customers.details.search.CustomerSearchHostUI
import ru.shafran.ui.customers.details.session.activation.SessionActivationHostUI
import ru.shafran.ui.customers.details.session.use.SessionUseHostUI
import ru.shafran.ui.view.ExtendedModalBottomSheet

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
        try {
            if (routerState.value.activeChild.instance is CustomerDetailsHost.Child.Hidden) {
                state.animateTo(ModalBottomSheetValue.Hidden, tween(50))
            } else {
                state.animateTo(ModalBottomSheetValue.Expanded)
            }
        } catch (e: Exception) {
            Napier.e({ "SheetStateError" }, e)
        }
    }
    )
    Box(modifier = modifier) {
        ExtendedModalBottomSheet(
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
            modifier = Modifier.fillMaxWidth(),
            content = content,
        )
    }
}


@Composable
private fun CustomerDetailsNavHost(
    child: CustomerDetailsHost.Child<Any?>,
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
        is CustomerDetailsHost.Child.SessionUse ->
            SessionUseHostUI(component = child.component, modifier = modifier)
        is CustomerDetailsHost.Child.GenerateCard ->
            CardGeneratorHostUI(component = child.component, modifier = modifier)
        is CustomerDetailsHost.Child.CardSender ->
            CustomerSenderUI(component = child.component, modifier = modifier)
        is CustomerDetailsHost.Child.CustomerSearch ->
            CustomerSearchHostUI(component = child.component, modifier = modifier)
    }
}

