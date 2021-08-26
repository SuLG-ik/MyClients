package ru.shafran.cards.ui.component.employees

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.cards.ui.component.employeeslist.EmployeesListUI

@Composable
fun EmployeesUI(component: Employees, modifier: Modifier = Modifier) {


    Children(routerState = component.routerState) {
        when (val instance = it.instance) {
            is Employees.Child.EmployeesList -> {
                EmployeesListUI(component = instance.employeesList, modifier = Modifier.fillMaxSize())
            }
        }
    }
}