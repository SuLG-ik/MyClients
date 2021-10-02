package ru.shafran.cards.ui.component.employees.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R
import ru.shafran.cards.data.employee.EmployeeDataModel
import ru.shafran.cards.data.employee.EmployeeModel
import ru.shafran.cards.ui.component.cardsdetails.info.MaterialDivider
import ru.shafran.cards.ui.component.cardsdetails.info.OutlinedSurface

@Composable
fun EditEmployeeUI(
    component: EditEmployee,
    modifier: Modifier,
) {
    EditEmployee(
        employee = component.employee,
        onEdit = component::onEditEmployee,
        onCancel = component::onCancel,
        modifier = modifier,
    )
}

@Composable
fun EditEmployee(
    employee: EmployeeModel,
    onEdit: (EmployeeDataModel) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier,
) {
    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CancelIcon(onCancel)
            Spacer(modifier = Modifier.width(10.dp))
            Text("Редактирование работника", style = MaterialTheme.typography.h5)
        }
        MaterialDivider(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        )
        OutlinedSurface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                var name by rememberSaveable { mutableStateOf(employee.data.name) }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Имя") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(onClick = {
                    onEdit(
                        EmployeeDataModel(
                            name = name,
                        )
                    )
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Подтвердить")
                }
            }
        }
    }
}

@Composable
fun CancelIcon(onCancel: () -> Unit, modifier: Modifier = Modifier) {
    Icon(
        painterResource(id = R.drawable.cancel_button),
        contentDescription = "cancel",
        modifier = Modifier
            .size(25.dp)
            .clickable(
                remember { MutableInteractionSource() },
                null,
                onClick = onCancel,
            ).then(modifier),
    )
}