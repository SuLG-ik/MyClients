package ru.shafran.cards.ui.component.details.info

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
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
    component.currentCard.collectAsState().value.let { result ->
        result?.apply {
            onSuccess { card ->
                SuccessCard(
                    card = card,
                    onActivate = { component.activateCardById(card.id, ActivationData(capacity = 5)) },
                    onDeactivate = { component.deactivateCardById(card.id, DeactivationData()) },
                    onUse = { component.useCardById(card.id, UsageData()) },
                    modifier = modifier,
                )
            }
            onFailure { }
        } ?: Loading(modifier)
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .padding(25.dp)
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
        Spacer(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(0.10f)
                .height(2.dp)
                .align(CenterHorizontally)
                .background(MaterialTheme.colors.onBackground.copy(alpha = 0.14f),
                    shape = MaterialTheme.shapes.small)
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
        OutlinedSurface(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Center) {
                CardHistory(
                    history = card.history,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                )
            }

        }
    }
}

private val previewCard = Card(
    rawToken = "fsdfasfsdfsd",
    id = 87978,
    CardDescription.Activated(CardAction.Activation()),
    history = CardHistory(0,
        listOf(
            CardAction.Activation(),
            CardAction.Usage(),
            CardAction.Usage(),
            CardAction.Usage(),
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
            modifier = modifier
        )
        is CardDescription.Activated -> CardDescriptionActivated(
            description = description,
            modifier = modifier
        )
        is CardDescription.Deactivated -> CardDescriptionDeactivated(
            description = description,
            modifier = modifier
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
fun CardDescriptionDeactivated(
    description: CardDescription.Deactivated,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text("Состояние: не активирована", style = MaterialTheme.typography.subtitle1)
        Text("Причина: ${description.deactivation.data.reason}",
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
        EmptyHistory(Modifier
            .fillMaxWidth()
            .padding(15.dp))
    } else {
        LazyColumn(state = state, modifier = modifier) {
            itemsIndexed(history.actions.reversed()) { index, item ->
                CardHistoryItem(action = item, modifier = Modifier.fillMaxWidth())
                if (index < history.actions.size) {
                    Spacer(modifier = Modifier.width(5.dp))
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
fun CardHistoryItem(action: CardAction, modifier: Modifier) {
    when (action) {
        is CardAction.Activation ->
            CardHistoryActivationItem(action = action, modifier = modifier)
        is CardAction.Deactivation ->
            CardHistoryDeactivationItem(action = action, modifier = modifier)
        is CardAction.Usage ->
            CardHistoryUsageItem(action = action, modifier = modifier)
    }
}

@Composable
fun CardHistoryActivationItem(action: CardAction.Activation, modifier: Modifier) {
    CardHistoryItem(title = "Активация",
        icon = painterResource(id = R.drawable.turn_on_slider),
        time = action.time)
}

@Composable
fun CardHistoryDeactivationItem(action: CardAction.Deactivation, modifier: Modifier) {
    CardHistoryItem(title = "Отключение",
        icon = painterResource(id = R.drawable.turn_off_slider),
        time = action.time)
}

@Composable
fun CardHistoryUsageItem(action: CardAction.Usage, modifier: Modifier) {
    CardHistoryItem(title = "Использование",
        icon = painterResource(id = R.drawable.use_card),
        time = action.time)
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
