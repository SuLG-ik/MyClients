package ru.shafran.cards.ui.component.employees

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.shafran.cards.ui.component.employeesdetails.EmployeesDetailsUI
import ru.shafran.cards.ui.component.employeeslist.EmployeesListUI

@Composable
fun EmployeesUI(component: Employees, modifier: Modifier = Modifier) {
    EmployeesDetailsUI(component = component.employeesDetails, modifier = modifier) {
        EmployeesListUI(component = component.employeesList, modifier = Modifier.fillMaxSize())
    }
}