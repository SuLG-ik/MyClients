package ru.shafran.ui.sessions.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.sessions.history.SessionsHistoryListHost
import ru.shafran.ui.customers.details.CustomerDetailsHostUI
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI


@Composable
fun SessionsHistoryListHostUI(component: SessionsHistoryListHost, modifier: Modifier) {
    CustomerDetailsHostUI(component = component.customerDetails, modifier) {
        Children(component.routerState) {
            SessionsHistoryListNavHost(child = it.instance, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun SessionsHistoryListNavHost(child: SessionsHistoryListHost.Child, modifier: Modifier) {
    when (child) {
        is SessionsHistoryListHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
        is SessionsHistoryListHost.Child.LastSessionsList ->
            SessionsHistoryListUI(component = child.component, modifier = modifier)
        is SessionsHistoryListHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}