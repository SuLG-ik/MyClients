package ru.shafran.ui.employees.picker

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.crossfade
import ru.shafran.common.employees.picker.EmployeePicker
import ru.shafran.network.employees.data.Employee
import ru.shafran.ui.R
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.StaticDropdownMenu

@Composable
internal fun FloatingEmployeePickerUI(component: EmployeePicker, modifier: Modifier) {
    Box(modifier = modifier) {
        EmployeePickerTitle(
            component.selectedEmployee.collectAsState().value,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = component::onTogglePicking)
        )
        EmployeePickerDropdown(
            component = component,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(400.dp),
        )
    }
}

@Composable
private fun EmployeePickerTitle(selectedEmployee: Employee?, modifier: Modifier) {
    OutlinedSurface(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            if (selectedEmployee == null) {
                EmptyEmployee(
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
}

@Composable
private fun EmptyEmployee(
    modifier: Modifier = Modifier,
) {
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

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun EmployeePickerDropdown(
    component: EmployeePicker,
    modifier: Modifier = Modifier,
) {
    StaticDropdownMenu(
        expanded = component.isPicking.collectAsState().value,
        onDismissRequest = { component.onTogglePicking(false) },
        modifier = modifier,
    ) {
        Children(
            routerState = component.routerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            animation = crossfade(animationSpec = tween(250)),
        ) {
            EmployeesPickerNavHost(child = it.instance,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun EmployeesPickerNavHost(child: EmployeePicker.Child, modifier: Modifier) {
    when (child) {
        is EmployeePicker.Child.EmployeeSelector ->
            EmployeeSelectorUI(component = child.component, modifier = modifier)
    }
}