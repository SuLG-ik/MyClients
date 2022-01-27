package ru.shafran.ui.details.info

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.shafran.common.details.info.InactivatedCustomerInfo
import ru.shafran.common.details.info.LoadedCustomerInfo
import ru.shafran.common.details.info.PreloadedCustomerInfo
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
internal fun LoadedCustomerDetailsUI(
    component: LoadedCustomerInfo,
    modifier: Modifier,
) {
    Column(
        modifier = modifier.verticalScroll(state = rememberScrollState()),
    ) {
        CustomerInfo(
            customer = component.customer.data,
            onEdit = component::onEdit,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(10.dp))
        SessionHistory(
            component.history,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
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
    ) {
        CustomerInfo(
            customer = component.customer.data,
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
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_employees),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp),
        )
        Spacer(
            modifier = Modifier.width(10.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(id)
            Spacer(
                modifier = Modifier.width(10.dp),
            )
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
private fun CustomerInfo(
    customer: CustomerData,
    modifier: Modifier = Modifier,
    onEdit: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_employees),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            Text(
                customer.name,
                maxLines = 1,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis,
            )
            Phone(customer.phone)
            Gender(customer.gender)
        }
        if (onEdit != null) {
            Box(modifier = Modifier
                .size(16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onEdit,
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_button),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp),
                )
            }
        }

    }
}

//TODO
@Composable
private fun SessionHistory(
    history: List<Session>,
    modifier: Modifier = Modifier,
) {
    if (history.isEmpty()) {
        OutlinedSurface(
            modifier = modifier,
        ) {
            EmptySessionHistory(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp))
        }
    } else {
        NotEmptySessionHistory(history = history, modifier = modifier)
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
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(history) { index, session ->
            SessionHistoryItem(
                session = session,
                modifier = Modifier.fillMaxWidth()
            )
            if (index < history.size - 1) {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun SessionHistoryItem(session: Session, modifier: Modifier = Modifier) {
    val isExpanded = remember { mutableStateOf(false) }
    OutlinedSurface(
        modifier = modifier
            .clickable { isExpanded.value = !isExpanded.value }
            .animateContentSize()
    ) {
        Column(
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
                Spacer(modifier = modifier.height(10.dp))
                MaterialDivider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = modifier.height(10.dp))
                SessionHistoryUsages(usages = session.usages, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun SessionHistoryUsages(usages: List<SessionUsage>, modifier: Modifier) {
    Column(modifier) {
        usages.forEach { usage ->
            SessionHistoryUsageItem(usage, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun SessionHistoryUsageItem(usage: SessionUsage, modifier: Modifier) {
    val timeFormatter = LocalTimeFormatter.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = R.drawable.logo_cards),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            Text(
                text = timeFormatter.format(usage.data.date),
                style = MaterialTheme.typography.h6,
            )
            Text(
                text = "Исполнитель: ${usage.data.employee.id}",
                style = MaterialTheme.typography.subtitle1,
            )
        }
    }

}


@Composable
private fun SessionItemHeader(session: Session, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = R.drawable.turn_on_slider),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            Text(session.activation.service.info.title,
                style = MaterialTheme.typography.h6)
            Text("Исполнитель: ${session.activation.employee.id}",
                style = MaterialTheme.typography.subtitle1)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text("${session.usages.size}/${session.activation.service.configuration.amount}")
    }
}
