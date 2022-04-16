package ru.shafran.ui.employees.details.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.employees.details.create.EmployeeCreatingHost
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI

@Composable
fun EmployeeCreatingHostUI(
    component: EmployeeCreatingHost,
    modifier: Modifier,
) {
    Children(routerState = component.routerState) {
        EmployeeCreatingNavHost(
            child = it.instance,
            modifier = modifier
        )
    }
}


@Composable
private fun EmployeeCreatingNavHost(
    child: EmployeeCreatingHost.Child,
    modifier: Modifier,
) {
    when (child) {
        is EmployeeCreatingHost.Child.CreateEmployee ->
            EmployeeCreatingUI(component = child.component, modifier = modifier)
        is EmployeeCreatingHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
        is EmployeeCreatingHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}