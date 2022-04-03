package ru.shafran.ui.customers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.customers.Customers
import ru.shafran.ui.sessions.history.SessionsHistoryListHostUI


@Composable
fun CustomersUI(customers: Customers, modifier: Modifier) {
    Children(routerState = customers.routerState) {
        CustomersNavHost(child = it.instance, modifier = modifier)
    }
}


@Composable
fun CustomersNavHost(child: Customers.Child, modifier: Modifier) {
    when (child) {
        is Customers.Child.SessionsHistory ->
            SessionsHistoryListHostUI(component = child.component, modifier = modifier)
    }
}