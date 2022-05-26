package ru.shafran.ui.customers.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shafran.common.customers.details.host.CustomerDetailsHost
import ru.shafran.ui.customers.details.edit.CustomerEditingHostUI
import ru.shafran.ui.customers.details.generator.CardGeneratorHostUI
import ru.shafran.ui.customers.details.generator.CustomerSenderUI
import ru.shafran.ui.customers.details.info.CustomerInfoHostUI
import ru.shafran.ui.customers.details.search.CustomerSearchHostUI
import ru.shafran.ui.customers.details.session.activation.SessionActivationHostUI
import ru.shafran.ui.customers.details.session.deactivation.SessionDeactivationHostUI
import ru.shafran.ui.customers.details.session.use.SessionUseHostUI
import ru.shafran.ui.sessions.stats.AllSessionStatsHostUI
import ru.shafran.ui.view.BottomSheetComponentUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CustomerDetailsHostUI(
    component: CustomerDetailsHost,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BottomSheetComponentUI(
        router = component.routerState,
        isHidden = { it is CustomerDetailsHost.Child.Hidden },
        onHide = component.onHide,
        modifier = modifier,
        navHost = {
            CustomerDetailsNavHost(
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
        is CustomerDetailsHost.Child.SessionDeactivate ->
            SessionDeactivationHostUI(component = child.component, modifier = modifier)
        is CustomerDetailsHost.Child.SessionsStats ->
            AllSessionStatsHostUI(component = child.component, modifier = modifier)
    }
}

