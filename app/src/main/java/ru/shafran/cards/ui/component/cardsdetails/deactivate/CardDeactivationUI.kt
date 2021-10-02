package ru.shafran.cards.ui.component.cardsdetails.deactivate

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.DeactivationDataModel
import ru.shafran.cards.ui.component.cardsdetails.info.MaterialDivider
import ru.shafran.cards.ui.component.cardsdetails.info.OutlinedSurface
import ru.shafran.cards.ui.component.employees.edit.CancelIcon

@Composable
fun CardDeactivationUI(component: CardDeactivation, modifier: Modifier = Modifier) {
    CardDeactivationRequest(
        card = component.card,
        onDeactivate = component::onDeactivate,
        onCancel = component::onCancel,
        modifier = modifier,
    )
}

@Composable
fun CardDeactivationRequest(
    card: CardModel,
    onDeactivate: (DeactivationDataModel) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier,
) {
    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CancelIcon(onCancel = onCancel)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Отключение карты: ${card.id}",
                style = MaterialTheme.typography.h5,)
        }
        MaterialDivider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp, bottom = 5.dp)
        )
        OutlinedSurface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                var note by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Заметка") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(onClick = {
                    onDeactivate(
                        DeactivationDataModel(
                            note = note,
                        )
                    )
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Подтвердить")
                }
            }
        }
    }
}