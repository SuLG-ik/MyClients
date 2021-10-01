package ru.shafran.cards.ui.component.cardsdetails.use

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.UsageDataModel
import ru.shafran.cards.data.employee.EmployeeModel
import ru.shafran.cards.ui.component.cardsdetails.info.MaterialDivider
import ru.shafran.cards.ui.component.cardsdetails.info.OutlinedSurface

@Composable
fun CardUsageUI(component: CardUsage, modifier: Modifier = Modifier) {
    CardDeactivationRequest(
        card = component.card,
        employees = component.employees.collectAsState(initial = null).value,
        onUse = component::onUse,
        onCancel = component::onCancel,
        modifier = modifier,
    )
}

@Composable
fun CardDeactivationRequest(
    card: CardModel,
    employees: List<EmployeeModel>?,
    onUse: (UsageDataModel) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier,
) {
    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(id = R.drawable.cancel_button),
                contentDescription = "cancel",
                modifier = Modifier
                    .size(25.dp)
                    .clickable(remember { MutableInteractionSource() }, null, onClick = onCancel))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Использовать карту: ${card.id}",
                style = MaterialTheme.typography.h5)
        }
        MaterialDivider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp, bottom = 5.dp)
        )
        OutlinedSurface(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                var isExpanded by remember { mutableStateOf(false) }
                var selectedEmployee by remember { mutableStateOf<EmployeeModel?>(null) }
                OutlinedSurface(
                    modifier = Modifier.fillMaxWidth().clickable(employees != null, onClick = {
                        isExpanded = true
                    })
                ) {
                    if (employees == null) {
                        CircularProgressIndicator()
                    } else {
                        Text(selectedEmployee?.data?.name ?: "Не указан", modifier = Modifier.padding(15.dp))
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false },
                        ) {
                            employees.forEach { employee ->
                                DropdownMenuItem(onClick = {
                                    isExpanded = false
                                    selectedEmployee = employee
                                }) {
                                    Text(employee.data.name)
                                }
                            }
                        }
                    }
                }
                var note by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Заметка") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
                        onUse(
                            UsageDataModel(
                                note = note,
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Подтвердить")
                }
            }
        }
    }
}