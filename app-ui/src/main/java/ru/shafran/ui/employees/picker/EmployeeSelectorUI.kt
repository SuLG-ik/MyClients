package ru.shafran.ui.employees.picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.employees.picker.employee.EmployeeSelector
import ru.shafran.common.employees.picker.employee.EmployeesListSelector
import ru.shafran.network.employees.data.Employee
import ru.shafran.ui.R
import ru.shafran.ui.loading.LoadingUI
import ru.shafran.ui.view.OutlinedTitledDialog

@Composable
internal fun EmployeeSelectorUI(component: EmployeeSelector, modifier: Modifier) {
    Children(routerState = component.routerState, modifier = modifier) {
        EmployeeSelectorNavHost(child = it.instance, modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun EmployeeSelectorNavHost(child: EmployeeSelector.Child, modifier: Modifier) {
    when (child) {
        is EmployeeSelector.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
        is EmployeeSelector.Child.EmployeeList ->
            EmployeesListUI(component = child.component, modifier = modifier)
    }
}

@Composable
private fun EmployeesListUI(component: EmployeesListSelector, modifier: Modifier) {
    EmployeesList(
        selectedEmployee = component.selectedEmployee?.id,
        employees = component.employees,
        onSelect = component::onSelect,
        modifier = modifier,
    )
}


@Composable
private fun EmployeesList(
    selectedEmployee: String?,
    employees: List<Employee>,
    onSelect: (Employee) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTitledDialog(
        title = {
            Text(
                stringResource(R.string.customer_session_activation_employees_title),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        contentPadding = PaddingValues(0.dp),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            employees.forEach { employee ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable { onSelect(employee) }) {
                    Employee(
                        employee = employee,
                        isSelected = selectedEmployee == employee.id,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun Employee(
    employee: Employee,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
        Text(
            employee.data.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}