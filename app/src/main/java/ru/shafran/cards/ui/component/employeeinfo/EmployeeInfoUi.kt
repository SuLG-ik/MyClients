package ru.shafran.cards.ui.component.employeeinfo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R
import ru.shafran.cards.data.employee.EmployeeModel

@Composable
fun EmployeeInfoUI(component: EmployeeInfo, modifier: Modifier) {
    EmployeeInfo(employee = component.employee, modifier = modifier)
}

@Composable
private fun EmployeeInfo(employee: EmployeeModel, modifier: Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.logo_employees),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
        )
        Text(text = employee.data.name)
    }
}
