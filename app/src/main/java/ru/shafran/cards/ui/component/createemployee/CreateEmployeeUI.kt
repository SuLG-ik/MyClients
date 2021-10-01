package ru.shafran.cards.ui.component.createemployee

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shafran.cards.data.employee.EmployeeDataModel
import ru.shafran.cards.ui.component.cardsdetails.info.MaterialDivider
import ru.shafran.cards.ui.component.cardsdetails.info.OutlinedSurface

@Composable
fun CreateEmployeeUI(
    component: CreateEmployee,
    modifier: Modifier,
) {
    CreateEmployee(onCreate = component::onCreateEmployee, modifier = modifier)
}

@Composable
fun CreateEmployee(
    onCreate: (EmployeeDataModel) -> Unit,
    modifier: Modifier,
) {
    Column(modifier) {
        Text("Новый работник", style = MaterialTheme.typography.h5)
        MaterialDivider(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        )
        OutlinedSurface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                var name by rememberSaveable { mutableStateOf("") }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Имя") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(onClick = {
                    onCreate(
                        EmployeeDataModel(
                            name = name,
                            null,
                        )
                    )
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Подтвердить")
                }
            }
        }
    }
}