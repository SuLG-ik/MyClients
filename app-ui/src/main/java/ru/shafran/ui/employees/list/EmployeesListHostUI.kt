package ru.shafran.ui.employees.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.employees.list.EmployeesListHost
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI

@Composable
fun EmployeesListHostUI(component: EmployeesListHost, modifier: Modifier) {
    Children(routerState = component.routerState) {
        EmployeesListNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun EmployeesListNavHost(child: EmployeesListHost.Child, modifier: Modifier) {
    when (child) {
        is EmployeesListHost.Child.EmployeesList ->
            EmployeesListUI(component = child.component, modifier = modifier)
        is EmployeesListHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
        is EmployeesListHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}