package ru.shafran.cards.ui.component.employees.delete

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.shafran.cards.data.employee.EmployeeModel

@Composable
fun DeleteEmployeeUI(
    component: DeleteEmployee,
    modifier: Modifier,
) {
    DeleteEmployee(
        component.employee,
        onAgree = component::onAgree,
        onCancel = component::onCancel,
        modifier = modifier,
    )
}

@Composable
fun DeleteEmployee(
    employeeModel: EmployeeModel,
    onAgree: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "${employeeModel.data.name}?",
            style = MaterialTheme.typography.h4,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            "Подтвердите удаление работника",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = onAgree,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    "Удалить",
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    "Отменить",
                )
            }
        }
    }
}