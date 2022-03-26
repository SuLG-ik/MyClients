package ru.shafran.ui.details.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.shafran.network.employees.data.Employee
import ru.shafran.ui.R
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.StaticDropdownMenu
import ru.shafran.ui.view.TitledDialog


@Composable
internal fun EmployeesSelector(
    selectedEmployee: Employee?,
    onSelect: (Employee) -> Unit,
    employees: List<Employee>,
    modifier: Modifier = Modifier,
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Box(modifier = modifier) {
        OutlinedSurface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded.value = true },
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                if (selectedEmployee == null) {
                    EmptyEmployeeConfiguration(
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                } else {
                    Employee(
                        employee = selectedEmployee,
                        isSelected = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }
        }
        EmployeesDropdown(
            isExpanded = isExpanded.value,
            onDismiss = { isExpanded.value = false },
            selectedEmployee = selectedEmployee,
            onSelect = onSelect,
            employees = employees,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(300.dp),
        )
    }
}

@Composable
private fun EmployeesDropdown(
    isExpanded: Boolean,
    selectedEmployee: Employee?,
    onDismiss: () -> Unit,
    onSelect: (Employee) -> Unit,
    employees: List<Employee>,
    modifier: Modifier = Modifier,
) {
    StaticDropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        val selectedService = remember(employees, selectedEmployee) {
            mutableStateOf(employees.firstOrNull { it.id == selectedEmployee?.id })
        }
        EmployeesList(
            selectedEmployee = selectedEmployee?.id,
            onSelect = {
                selectedService.value = it
                onSelect(it)
            },
            employees = employees,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        )
    }
}

@Composable
private fun EmptyEmployeeConfiguration(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(Icons.Outlined.Close, contentDescription = null)
        Text(stringResource(R.string.customer_session_activation_empty_employee))
    }
}

@Composable
private fun EmployeesList(
    selectedEmployee: String?,
    employees: List<Employee>,
    onSelect: (Employee) -> Unit,
    modifier: Modifier = Modifier,
) {
    TitledDialog(
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