package ru.shafran.ui.details.info

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import ru.shafran.common.details.info.InactivatedCustomerInfo
import ru.shafran.common.details.info.LoadedCustomerInfo
import ru.shafran.common.details.info.PreloadedCustomerInfo
import ru.shafran.common.loading.Loading
import ru.shafran.network.customers.data.CustomerData
import ru.shafran.network.session.data.Session
import ru.shafran.network.session.data.SessionUsage
import ru.shafran.ui.R
import ru.shafran.ui.utils.LocalTimeFormatter
import ru.shafran.ui.view.Gender
import ru.shafran.ui.view.MaterialDivider
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.Phone


@Composable
internal fun LoadingCustomerDetailsUI(
    component: Loading,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        PlaceholderCustomerInfo(
            modifier = modifier,
        )
        PlaceholderSessionHistory(modifier = Modifier.fillMaxWidth())
    }
}


@Composable
internal fun InactivatedCustomerDetailsUI(
    component: InactivatedCustomerInfo,
    modifier: Modifier,
) {
    InactivatedCustomerInfo(
        id = component.customer.id,
        onActivate = component::onEdit,
        modifier = modifier,
    )
}


@Composable
fun PlaceholderCustomerInfo(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_employees),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .placeholder(true, highlight = PlaceholderHighlight.fade())
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            Text(
                "Имя Фамилия",
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .placeholder(true, highlight = PlaceholderHighlight.fade())
            )
            Phone(null, modifier = Modifier
                .placeholder(true, highlight = PlaceholderHighlight.fade()))
            Gender(ru.shafran.network.Gender.UNKNOWN, modifier = Modifier
                .placeholder(true, highlight = PlaceholderHighlight.fade()))
        }
    }
}


@Composable
internal fun LoadedCustomerDetailsUI(
    component: LoadedCustomerInfo,
    modifier: Modifier,
) {
    Column(
        modifier = modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CustomerInfo(
            customer = component.customer.data,
            onEdit = component::onEdit,
            onShare = component::onShareCard,
            modifier = Modifier.fillMaxWidth(),
        )
        SessionHistory(
            component.history,
            onUse = component::onUseSession,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(unbounded = true)
        )
        OutlinedButton(
            onClick = component::onActivateSession,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.customers_create_new_session),
            )
        }
    }
}


@Composable
internal fun PreloadedCustomerDetailsUI(
    component: PreloadedCustomerInfo,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        CustomerInfo(
            customer = component.customer.data,
        )
        PlaceholderSessionHistory(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(unbounded = true)
        )
    }
}

@Composable
private fun InactivatedCustomerInfo(
    id: String,
    onActivate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_employees),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(id)
            OutlinedButton(
                onClick = onActivate,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(stringResource(R.string.customers_activate_button))
            }
        }
    }
}


@Composable
fun CustomerInfo(
    customer: CustomerData,
    modifier: Modifier = Modifier,
    onShare: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_employees),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            Text(
                customer.name,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
            )
            Phone(customer.phone)
            Gender(customer.gender)
        }
        Row {
            if (onShare != null) {
                IconButton(onClick = onShare) {
                    Icon(
                        Icons.Outlined.Share,
                        contentDescription = null,
                    )
                }
            }
            if (onEdit != null) {
                IconButton(onEdit) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_button),
                        contentDescription = null,
                    )
                }
            }
        }

    }
}

//TODO
@Composable
private fun SessionHistory(
    history: List<Session>,
    onUse: (Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (history.isEmpty()) {
        OutlinedSurface(
            modifier = modifier,
        ) {
            EmptySessionHistory(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
            )
        }
    } else {
        NotEmptySessionHistory(
            history = history,
            onUse = onUse,
            modifier = modifier
        )
    }
}

@Composable
private fun EmptySessionHistory(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Outlined.Info, contentDescription = null)
        Text(stringResource(R.string.customer_empty_history))
    }
}

@Composable
private fun NotEmptySessionHistory(
    history: List<Session>,
    onUse: (Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        history.forEach { session ->
            SessionHistoryItem(
                session = session,
                onUse = { onUse(session) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PlaceholderSessionHistory(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(3) {
            PlaceholderHistoryItem(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
private fun PlaceholderHistoryItem(modifier: Modifier = Modifier) {
    OutlinedSurface(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .animateContentSize(),
        ) {
            PlaceholderItemHeader(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PlaceholderItemHeader(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = R.drawable.turn_on_slider),
            contentDescription = null,
            modifier = Modifier
                .size(25.dp)
                .placeholder(
                    true, highlight = PlaceholderHighlight.fade(),
                )
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            Text(
                text = "Название тут",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .placeholder(true, highlight = PlaceholderHighlight.fade())
            )
            Text(
                text = "Исполнитель: да он тут",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .placeholder(true, highlight = PlaceholderHighlight.fade())
            )
        }
        Text(
            text = "2/2",
            modifier = Modifier
                .placeholder(true, highlight = PlaceholderHighlight.fade())
        )
    }
}


@Composable
private fun SessionHistoryItem(
    session: Session,
    onUse: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isExpanded = rememberSaveable { mutableStateOf(false) }
    val isHistoryShown = rememberSaveable { mutableStateOf(false) }
    OutlinedSurface(
        modifier = modifier
            .clickable { isExpanded.value = !isExpanded.value }
            .animateContentSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .animateContentSize(),
        ) {
            SessionItemHeader(
                session = session,
                modifier = Modifier.fillMaxWidth()
            )
            AnimatedVisibility(isExpanded.value) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    MaterialDivider(modifier = Modifier.fillMaxWidth())
                    SessionActions(
                        isUsable = !session.isDeactivated,
                        onShowHistory = { isHistoryShown.value = !isHistoryShown.value },
                        onUse = onUse,
                        modifier = Modifier
                            .align(Alignment.End)
                    )
                    AnimatedVisibility(visible = isHistoryShown.value) {
                        SessionHistoryUsages(usages = session.usages,
                            modifier = Modifier.fillMaxWidth())
                    }
                }
            }

        }
    }
}

@Composable
private fun SessionActions(
    isUsable: Boolean,
    onUse: () -> Unit,
    onShowHistory: () -> Unit,
    modifier: Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {
        IconButton(onClick = onShowHistory) {
            Icon(Icons.Outlined.List, contentDescription = null)
        }
        if (isUsable)
            IconButton(onClick = onUse) {
                Icon(Icons.Outlined.Check, contentDescription = null)
            }
    }
}


@Composable
private fun EmptySessionItems(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Outlined.Info, contentDescription = null)
        Text(stringResource(R.string.customer_empty_history))
    }
}

@Composable
private fun SessionHistoryUsages(usages: List<SessionUsage>, modifier: Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (usages.isEmpty()) {
            EmptySessionItems(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            usages.forEach { usage ->
                SessionHistoryUsageItem(usage, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun SessionHistoryUsageItem(usage: SessionUsage, modifier: Modifier) {
    val timeFormatter = LocalTimeFormatter.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = R.drawable.logo_cards),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            Text(
                text = timeFormatter.format(usage.data.date),
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = "Исполнитель: ${usage.data.employee.id}",
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }

}


@Composable
private fun SessionItemHeader(session: Session, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = R.drawable.turn_on_slider),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            Text(session.activation.service.info.title,
                style = MaterialTheme.typography.labelMedium)
            Text("Исполнитель: ${session.activation.employee.id}",
                style = MaterialTheme.typography.labelSmall)
        }
        Text("${session.usages.size}/${session.activation.service.configuration.amount}")
    }
}
