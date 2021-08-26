package ru.shafran.cards.ui.component.details.info

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R
import ru.shafran.cards.data.card.*
import ru.shafran.cards.utils.format
import java.time.ZonedDateTime

@Composable
fun CardInfoUI(component: CardInfo, modifier: Modifier) {
    val card by component.currentCard.collectAsState()

    SuccessCard(
        card = card,
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
                .background(MaterialTheme.colors.onBackground.copy(alpha = 0.14f),
                    shape = MaterialTheme.shapes.small)
        )
    }
}


@Composable
fun SuccessCard(
    card: Card,
    onActivate: (Card) -> Unit,
    onDeactivate: (Card) -> Unit,
    onUse: (Card) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(text = "Номер карты: ${card.id}", style = MaterialTheme.typography.h5)
        MaterialDivider(Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp))
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

//        if (isHistoryShown) {
            CardHistory(
                history = card.history,
                modifier = Modifier
                    .fillMaxWidth(),
            )
//        } else {
//            OutlinedButton(onClick = { isHistoryShown = true }, modifier = Modifier
//                .fillMaxWidth()) {
//                Text("Показать историю", overflow = TextOverflow.Ellipsis, maxLines = 1)
//            }
//        }
    }
}

private val previewCard = Card(
    rawToken = "fsdfasfsdfsd",
    id = 87978,
    history = CardHistory(9,
        listOf(
            CardAction.Activation(data = ActivationData(capacity = 5, cost = 500), id = 1),
            CardAction.Usage(data = UsageData(), id = 1, activationId = 1),
            CardAction.Usage(data = UsageData(), id = 2, activationId = 1),
            CardAction.Usage(data = UsageData(), id = 3, activationId = 1),
            CardAction.Deactivation(data = DeactivationData(), id = 1, activationId = 1),
            CardAction.Activation(data = ActivationData(capacity = 5, cost = 500), id = 2),
            CardAction.Usage(data = UsageData(), id = 4, activationId = 2),
            CardAction.Usage(data = UsageData(), id = 5, activationId = 2),
            CardAction.Activation(data = ActivationData(capacity = 5, cost = 500), id = 4),
            CardAction.Usage(data = UsageData(), id = 4, activationId = 4),
            CardAction.Usage(data = UsageData(), id = 5, activationId = 4),
            CardAction.Activation(data = ActivationData(capacity = 5, cost = 500), id = 5),
            CardAction.Usage(data = UsageData(), id = 4, activationId = 5),
            CardAction.Usage(data = UsageData(), id = 5, activationId = 5),
            CardAction.Activation(data = ActivationData(capacity = 5, cost = 500), id = 6),
            CardAction.Usage(data = UsageData(), id = 4, activationId = 6),
            CardAction.Usage(data = UsageData(), id = 5, activationId = 6),
            CardAction.Activation(data = ActivationData(capacity = 5, cost = 500), id = 7),
            CardAction.Usage(data = UsageData(), id = 4, activationId = 7),
            CardAction.Usage(data = UsageData(), id = 5, activationId = 8),
            CardAction.Activation(data = ActivationData(capacity = 5, cost = 500), id = 3),
            CardAction.Usage(data = UsageData(), id = 4, activationId = 3),
            CardAction.Usage(data = UsageData(), id = 5, activationId = 3),
            CardAction.Usage(data = UsageData(), id = 5, activationId = 3),
            CardAction.Usage(data = UsageData(), id = 5, activationId = 3),
            CardAction.Usage(data = UsageData(), id = 5, activationId = 3),
        )),
)

@Composable
@Preview(showBackground = true)
fun SuccessCardPreview() {
    SuccessCard(
        card = previewCard,
        onActivate = {},
        onDeactivate = {},
        onUse = {},
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun CardDescription(description: CardDescription, modifier: Modifier = Modifier) {
    when (description) {
        is CardDescription.NewerUsed -> CardDescriptionNewerUsed(
            description = description,
            modifier = modifier,
        )
        is CardDescription.Activated -> CardDescriptionActivated(
            description = description,
            modifier = modifier,
        )
        is CardDescription.Deactivated -> CardDescriptionDeactivated(
            description = description,
            modifier = modifier,
        )
        is CardDescription.Overuse -> CardDescriptionOveruse(
            description = description,
            modifier = modifier,
        )
    }
}

@Composable
fun CardDescriptionActivated(
    description: CardDescription.Activated,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text("Состояние: активирована",
            style = MaterialTheme.typography.subtitle1)
        Text("Услуга: ${description.activation.data.type.type}",
            style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun CardDescriptionOveruse(
    description: CardDescription.Overuse,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text("Состояние: достигнут лимит", style = MaterialTheme.typography.subtitle1)
        Text("Оказанная услуга: ${description.activation.data.type.type}",
            style = MaterialTheme.typography.subtitle1)
    }
}


@Composable
fun CardDescriptionDeactivated(
    description: CardDescription.Deactivated,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text("Состояние: отключена вручную", style = MaterialTheme.typography.subtitle1)
        if (description.deactivation.data.note.isNotEmpty())
            Text("Заметка: ${description.deactivation.data.note}",
                style = MaterialTheme.typography.subtitle1)
        Text("Оказанная услуга: ${description.activation.data.type.type}",
            style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun CardDescriptionNewerUsed(
    description: CardDescription.NewerUsed,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text("Состояние: не активирована",
            style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun OutlinedSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground.copy(0.14f)),
        content = content
    )
}

@Composable
fun CardHistory(history: CardHistory, modifier: Modifier = Modifier) {
    val state = rememberLazyListState()
    if (history.size == 0) {
        OutlinedSurface {
            EmptyHistory(Modifier
                .fillMaxWidth()
                .padding(15.dp))
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
                    CardHistorySection(isFull = isFull,
                        history = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .animateContentSize())
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
        Image(painterResource(id = R.drawable.card_history),
            contentDescription = "None",
            modifier = Modifier.size(50.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text("История пуста")
    }
}

@Composable
fun CardHistorySection(isFull: Boolean, history: MappedHistory, modifier: Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Text("Услуга: ${history.activation.data.type.type}",
                    style = MaterialTheme.typography.h6)
                if (isFull)
                    Text(buildTimeInfo(history), style = MaterialTheme.typography.subtitle1)
            }
            Text("${history.usages.size}/${history.activation.data.capacity}",
                style = MaterialTheme.typography.h6)
        }
        if (isFull && history.usages.isNotEmpty())
            MaterialDivider(Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp, bottom = 5.dp))

        if (isFull) {
            history.usages.forEachIndexed { index, item ->
                CardHistoryUsageItem(action = item, modifier = Modifier.fillMaxWidth())
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
fun CardHistoryActivationItem(action: CardAction.Activation, modifier: Modifier) {
    CardHistoryItem(title = "Активация",
        icon = painterResource(id = R.drawable.turn_on_slider),
        time = action.time, modifier = modifier)
}

@Composable
fun CardHistoryDeactivationItem(action: CardAction.Deactivation, modifier: Modifier) {
    CardHistoryItem(title = "Отключение",
        icon = painterResource(id = R.drawable.turn_off_slider),
        time = action.time, modifier = modifier)
}

@Composable
fun CardHistoryUsageItem(action: CardAction.Usage, modifier: Modifier) {
    CardHistoryItem(title = "Использование",
        icon = painterResource(id = R.drawable.use_card),
        time = action.time, modifier = modifier)
}

@Composable
fun CardHistoryItem(
    title: String,
    icon: Painter,
    time: ZonedDateTime,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Icon(painter = icon, contentDescription = "img")
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            Text(text = title, style = MaterialTheme.typography.h6)
            Text(time.format(), style = MaterialTheme.typography.subtitle1)
        }
    }
}


@Composable
fun CardActionBarPreview() {
    CardActionBar(previewCard, onActivate = {},
        onDeactivate = {},
        onUse = {},
        modifier = Modifier.fillMaxWidth())
}


@Composable
fun CardActionBar(
    card: Card,
    onActivate: (Card) -> Unit,
    onDeactivate: (Card) -> Unit,
    onUse: (Card) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        OutlinedButton(onClick = { onActivate(card) }, modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Text("Активировать", overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
        if (card.description is CardDescription.Activated) {
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedButton(onClick = { onDeactivate(card) }, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Text("Отключить", overflow = TextOverflow.Ellipsis, maxLines = 1)
            }
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedButton(onClick = { onUse(card) }, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Text("Использовать", overflow = TextOverflow.Ellipsis, maxLines = 1)
            }
        }
    }
}
