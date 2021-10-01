package ru.shafran.cards.ui.component.cardsdetails.info

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R
import ru.shafran.cards.data.card.*
import ru.shafran.cards.data.employee.EmployeeModel
import ru.shafran.cards.utils.format
import java.time.ZonedDateTime

@Composable
fun CardInfoUI(component: CardInfo, modifier: Modifier) {

    SuccessCard(
        card = component.currentCard,
        onUse = { component.onUse() },
        onActivate = { component.onActivate() },
        onDeactivate = { component.onDeactivate() },
        modifier = modifier,
    )
}


@Composable
fun MaterialDivider(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.20f)
                .height(2.dp)
                .align(Center)
                .background(
                    MaterialTheme.colors.onBackground.copy(alpha = 0.14f),
                    shape = MaterialTheme.shapes.small
                )
        )
    }
}


@Composable
fun SuccessCard(
    card: CardModel,
    onActivate: (CardModel) -> Unit,
    onDeactivate: (CardModel) -> Unit,
    onUse: (CardModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(text = "Номер карты: ${card.id}", style = MaterialTheme.typography.h5)
        MaterialDivider(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        )
        OutlinedSurface {
            CardDescription(
                description = card.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        CardActionBar(
            card = card,
            onActivate = onActivate,
            onDeactivate = onDeactivate,
            onUse = onUse,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        var isHistoryShown by remember(card.history.size) { mutableStateOf(card.history.size == 0) }

        if (isHistoryShown) {
            CardHistory(
                history = card.history,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        } else {
            OutlinedButton(onClick = { isHistoryShown = true }, modifier = Modifier
                .fillMaxWidth()) {
                Text("Показать историю", overflow = TextOverflow.Ellipsis, maxLines = 1)
            }
        }
    }
}

@Composable
fun CardDescription(description: CardDescriptionModel, modifier: Modifier = Modifier) {
    when (description) {
        is CardDescriptionModel.NewerUsed -> CardDescriptionNewerUsed(
            description = description,
            modifier = modifier,
        )
        is CardDescriptionModel.Activated -> CardDescriptionActivated(
            description = description,
            modifier = modifier,
        )
        is CardDescriptionModel.Deactivated -> CardDescriptionDeactivated(
            description = description,
            modifier = modifier,
        )
        is CardDescriptionModel.Overuse -> CardDescriptionOveruse(
            description = description,
            modifier = modifier,
        )
    }
}

@Composable
fun CardDescriptionActivated(
    description: CardDescriptionModel.Activated,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            "Состояние: активирована",
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            "Услуга: ${description.activation.data.type.type}",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun CardDescriptionOveruse(
    description: CardDescriptionModel.Overuse,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text("Состояние: достигнут лимит", style = MaterialTheme.typography.subtitle1)
        Text(
            "Оказанная услуга: ${description.activation.data.type.type}",
            style = MaterialTheme.typography.subtitle1
        )
    }
}


@Composable
fun CardDescriptionDeactivated(
    description: CardDescriptionModel.Deactivated,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text("Состояние: отключена вручную", style = MaterialTheme.typography.subtitle1)
        if (description.deactivation.data.note.isNotEmpty())
            Text(
                "Заметка: ${description.deactivation.data.note}",
                style = MaterialTheme.typography.subtitle1
            )
        Text(
            "Оказанная услуга: ${description.activation.data.type.type}",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun CardDescriptionNewerUsed(
    description: CardDescriptionModel.NewerUsed,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            "Состояние: не активирована",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun OutlinedSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .then(modifier),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground.copy(0.14f)),
        content = content
    )
}

@Composable
fun CardHistory(history: CardHistoryModel, modifier: Modifier = Modifier) {
    val state = rememberLazyListState()
    if (history.size == 0) {
        OutlinedSurface {
            EmptyHistory(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
    } else {
        LazyColumn(state = state, modifier = modifier) {
            val mappedHistory = history.actions.toMappedHistory().reversed()
            itemsIndexed(mappedHistory) { index, item ->
                var isFull by remember(index) { mutableStateOf(index == 0) }
                OutlinedSurface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .toggleable(isFull, onValueChange = { isFull = it })
                        .animateContentSize()
                ) {
                    CardHistorySection(
                        isFull = isFull,
                        history = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .animateContentSize()
                    )
                }
                if (index < mappedHistory.size) {
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}


@Composable
fun EmptyHistory(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(id = R.drawable.card_history),
            contentDescription = "None",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("История пуста")
    }
}

@Composable
fun CardHistorySection(isFull: Boolean, history: MappedHistory, modifier: Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    "Услуга: ${history.activation.data.type.type}",
                    style = MaterialTheme.typography.h6
                )
                if (isFull)
                    Text(buildTimeInfo(history), style = MaterialTheme.typography.subtitle1)
            }
            Text(
                "${history.usages.size}/${history.activation.data.capacity}",
                style = MaterialTheme.typography.h6
            )
        }
        if (isFull && history.usages.isNotEmpty())
            MaterialDivider(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 5.dp, bottom = 5.dp)
            )

        if (isFull) {
            history.usages.forEachIndexed { index, item ->
                CardHistoryUsageItem(action = item, employee = null,modifier = Modifier.fillMaxWidth())
                if (index < history.usages.size) {
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }

}

private fun buildTimeInfo(history: MappedHistory): String {
    return buildString {
        append(history.activation.time.format())
        if (history.deactivation != null) {
            append(" - ${history.deactivation.time.format()}")
        } else if (history.usages.size >= history.activation.data.capacity) {
            append(" - ${history.usages.lastOrNull()?.time?.format()}")
        }
    }
}

@Composable
fun CardHistoryActivationItem(action: CardActionModel.Activation, modifier: Modifier) {
    CardHistoryItem(
        title = "Активация",
        employee = null,
        icon = painterResource(id = R.drawable.turn_on_slider),
        time = action.time, modifier = modifier
    )
}

@Composable
fun CardHistoryDeactivationItem(action: CardActionModel.Deactivation, modifier: Modifier) {
    CardHistoryItem(
        title = "Отключение",
        employee = null,
        icon = painterResource(id = R.drawable.turn_off_slider),
        time = action.time, modifier = modifier
    )
}

@Composable
fun CardHistoryUsageItem(action: CardActionModel.Usage, employee: EmployeeModel?, modifier: Modifier) {
    CardHistoryItem(
        title = "Использование",
        employee = employee,
        icon = painterResource(id = R.drawable.use_card),
        time = action.time, modifier = modifier
    )
}

@Composable
fun CardHistoryItem(
    title: String,
    employee: EmployeeModel?,
    icon: Painter,
    time: ZonedDateTime,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Icon(painter = icon, contentDescription = "img")
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            Text(text = title, style = MaterialTheme.typography.h6)
            if (employee != null) {
                Text(
                    text = "Исполнитель: ${employee.data.name}",
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Text(time.format(), style = MaterialTheme.typography.subtitle1)
        }
    }
}


@Composable
fun CardActionBar(
    card: CardModel,
    onActivate: (CardModel) -> Unit,
    onDeactivate: (CardModel) -> Unit,
    onUse: (CardModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        OutlinedButton(
            onClick = { onActivate(card) }, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text("Активировать", overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
        if (card.description is CardDescriptionModel.Activated) {
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedButton(
                onClick = { onDeactivate(card) }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text("Отключить", overflow = TextOverflow.Ellipsis, maxLines = 1)
            }
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedButton(
                onClick = { onUse(card) }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text("Использовать", overflow = TextOverflow.Ellipsis, maxLines = 1)
            }
        }
    }
}
