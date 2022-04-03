package ru.shafran.ui.employees

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.employees.Employees
import ru.shafran.ui.employees.list.EmployeesListHostUI

@Composable
fun EmployeesUI(component: Employees, modifier: Modifier) {
    Children(routerState = component.routerState) {
        EmployeesNavHostUI(child = it.instance, modifier = modifier)
    }
}

@Composable
fun EmployeesNavHostUI(child: Employees.Child, modifier: Modifier) {
    when (child) {
        is Employees.Child.EmployeeList -> EmployeesListHostUI(child.component, modifier)
    }
}

