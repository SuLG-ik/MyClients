package ru.shafran.ui.employees.details.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.employees.details.info.EmployeeInfo
import ru.shafran.common.employees.details.info.EmployeeInfoHost
import ru.shafran.network.employees.data.Employee
import ru.shafran.ui.R
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI
import ru.shafran.ui.view.TitledDialog
import ru.shafran.ui.view.stringResource

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun EmployeeInfoHostUI(
    component: EmployeeInfoHost,
    modifier: Modifier,
) {
    Children(routerState = component.routerState) {
        EmployeeInfoNavHost(child = it.instance, modifier = modifier)
    }
}


@Composable
private fun EmployeeInfoNavHost(
    child: EmployeeInfoHost.Child,
    modifier: Modifier,
) {
    when (child) {
        is EmployeeInfoHost.Child.EmployeeLoaded ->
            EmployeeInfoUI(child.component, modifier)
        is EmployeeInfoHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
        is EmployeeInfoHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}

@Composable
private fun EmployeeInfoUI(
    component: EmployeeInfo,
    modifier: Modifier,
) {
    TitledDialog(
        title = {
            Text(stringResource(id = R.string.employee_title))
        },
        onBackPressed = component.onBack,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier,
    ) {
        EmployeeInfoHeader(
            employee = component.employee,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun EmployeeInfoHeader(
    employee: Employee,
    modifier: Modifier = Modifier,
    onEdit: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        EmployeeInfo(
            employee = employee,
            modifier = modifier
        )
        if (onEdit != null) {
            IconButton(onClick = onEdit) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_button),
                    contentDescription = null,
                )
            }
        }
    }
}


@Composable
fun EmployeeInfo(
    employee: Employee,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
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

@Composable
fun EmployeeDetails(employee: Employee, modifier: Modifier = Modifier) {
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