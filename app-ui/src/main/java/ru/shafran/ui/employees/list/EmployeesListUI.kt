package ru.shafran.ui.employees.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.shafran.common.employees.list.EmployeesList
import ru.shafran.network.employees.data.Employee
import ru.shafran.ui.R
import ru.shafran.ui.employees.details.info.EmployeeInfo
import ru.shafran.ui.view.OutlinedSurface

@Composable
fun EmployeesListUI(component: EmployeesList, modifier: Modifier) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = component.onCreateEmployee) {
                Icon(
                    painterResource(id = R.drawable.new_employee),
                    contentDescription = null,
                )
            }
        },
        modifier = modifier,

        ) {
        EmployeesList(
            employees = component.employee,
            onUpdate = component.onUpdate,
            onSelect = component.onSelect,
            modifier = modifier
        )
    }
}

@Composable
fun EmployeesList(
    employees: List<Employee>,
    onUpdate: () -> Unit,
    onSelect: (Employee) -> Unit,
    modifier: Modifier,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = onUpdate
    ) {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(employees) {
                EmployeesListItem(
                    employee = it,
                    onClick = { onSelect(it) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun EmployeesListItem(
    employee: Employee,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    OutlinedSurface(
        modifier = modifier,
        onClick = onClick,
    ) {
        EmployeeInfo(
            employee = employee,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
    }
}

