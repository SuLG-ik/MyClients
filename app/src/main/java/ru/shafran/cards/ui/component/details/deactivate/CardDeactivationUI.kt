package ru.shafran.cards.ui.component.details.deactivate

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.DeactivationDataModel
import ru.shafran.cards.ui.component.details.info.MaterialDivider
import ru.shafran.cards.ui.component.details.info.OutlinedSurface

@Composable
fun CardDeactivationUI(component: CardDeactivation, modifier: Modifier = Modifier) {
    val card by component.card.collectAsState()
    CardDeactivationRequest(
        card = card,
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
            Image(painterResource(id = R.drawable.cancel_button),
                contentDescription = "cancel",
                modifier = Modifier
                    .size(25.dp)
                    .clickable(remember { MutableInteractionSource() }, null, onClick = onCancel))
            Spacer(modifier = Modifier.width(5.dp))
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