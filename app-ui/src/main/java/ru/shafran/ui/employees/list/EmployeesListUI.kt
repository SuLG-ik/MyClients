package ru.shafran.ui.employees.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.shafran.common.employees.list.EmployeesList
import ru.shafran.network.employees.data.Employee
import ru.shafran.ui.R
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.stringResource

@Composable
fun EmployeesListUI(component: EmployeesList, modifier: Modifier) {
    EmployeesList(component.employee, component.onUpdate, modifier)
}

@Composable
fun EmployeesList(employees: List<Employee>, onUpdate: () -> Unit, modifier: Modifier){
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
                    onClick = {},
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
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.single_employee),
                    contentDescription = null,
                    modifier = Modifier.size(75.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, false),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        employee.data.name,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    EmployeeDetails(employee, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun EmployeeDetails(employee: Employee, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_gender),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(
            stringResource(employee.data.gender.stringResource),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}