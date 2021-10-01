package ru.shafran.cards.ui.component.cardsdetails.activation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R
import ru.shafran.cards.data.card.ActivationDataModel
import ru.shafran.cards.data.card.CardDescriptionModel
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.description
import ru.shafran.cards.ui.component.cardsdetails.info.MaterialDivider
import ru.shafran.cards.ui.component.cardsdetails.info.OutlinedSurface

@Composable
fun CardActivationUI(component: CardActivation, modifier: Modifier = Modifier) {
    CardActivationRequest(
        card = component.card,
        onActivate = component::onActivate,
        onCancel = component::onCancel,
        modifier = modifier
    )
}

private val regex = '0'..'9'

@Composable
fun CardActivationRequest(
    card: CardModel,
    onActivate: (ActivationDataModel) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier,
) {
    Column(modifier) {
        val previousActivationData = remember(card) {
            when (val description = card.description) {
                is CardDescriptionModel.Activated -> description.activation
                is CardDescriptionModel.Deactivated -> description.activation
                is CardDescriptionModel.Overuse -> description.activation
                else -> null
            }?.data
        }

        var cost by remember(previousActivationData) {
            mutableStateOf(previousActivationData?.cost?.toString() ?: "")
        }
        var capacity by remember(previousActivationData) {
            mutableStateOf(previousActivationData?.capacity?.toString() ?: "")
        }
        var isCostError by remember { mutableStateOf(false) }

        var isCapacityError by remember { mutableStateOf(false) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(id = R.drawable.cancel_button),
                contentDescription = "cancel",
                modifier = Modifier
                    .size(25.dp)
                    .clickable(remember { MutableInteractionSource() }, null, onClick = onCancel))
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Активация карты: ${card.id}",
                style = MaterialTheme.typography.h5,
            )
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
                Row {
                    OutlinedTextField(
                        value = cost,
                        onValueChange = { value ->
                            if (value.length >= 9) return@OutlinedTextField
                            val filtered = value.filter { it in regex }
                            isCostError = filtered.isEmpty() || filtered.toInt() == 0

                            cost = filtered
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                        isError = isCostError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        label = { Text("Стоимость") }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = capacity,
                        onValueChange = { value ->
                            if (value.length >= 9) return@OutlinedTextField
                            val filtered = value.filter { it in regex }
                            isCapacityError = filtered.isEmpty() || filtered.toInt() == 0

                            capacity = filtered
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                        isError = isCapacityError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        maxLines = 1,
                        label = { Text("Использования") }
                    )
                }
                var note by remember { mutableStateOf("") }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Заметка") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(onClick = {
                    if (cost.isEmpty()) {
                        isCostError = true
                    }
                    if (capacity.isEmpty() || capacity.toInt() <= 0) {
                        isCapacityError = true
                    }
                    if (!isCostError && !isCapacityError) {
                        onActivate(
                            ActivationDataModel(
                                cost = cost.toInt(),
                                capacity = capacity.toInt(),
                                note = note
                            )
                        )
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Подтвердить")
                }
            }
        }
    }
}