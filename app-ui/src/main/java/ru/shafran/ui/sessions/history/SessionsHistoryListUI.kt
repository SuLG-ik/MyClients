package ru.shafran.ui.sessions.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.shafran.common.sessions.history.SessionsHistoryList
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.session.data.SessionUsage
import ru.shafran.network.session.data.SessionUsageHistoryItem
import ru.shafran.ui.R
import ru.shafran.ui.utils.LocalTimeFormatter
import ru.shafran.ui.view.GenderIcon
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.Phone


@Composable
fun SessionsHistoryListUI(component: SessionsHistoryList, modifier: Modifier) {
    SessionsHistoryList(
        component.history,
        onUpdate = component.onUpdate,
        onDetails = component.onDetails,
        onStats = component.onStats,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionsHistoryList(
    history: List<SessionUsageHistoryItem>,
    onUpdate: () -> Unit,
    onDetails: (Customer.ActivatedCustomer) -> Unit,
    onStats: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onStats) {
                Icon(
                    painterResource(id = R.drawable.stats),
                    contentDescription = null,
                )
            }
        },
        modifier = modifier,
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = onUpdate,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(history) { item ->
                    val isExpanded = rememberSaveable { mutableStateOf(false) }
                    SessionsHistoryItem(
                        item = item,
                        isExpanded = isExpanded.value,
                        onClick = { isExpanded.value = !isExpanded.value },
                        onDetails = { onDetails(item.customer) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun SessionsHistoryItem(
    item: SessionUsageHistoryItem,
    isExpanded: Boolean,
    onClick: () -> Unit,
    onDetails: () -> Unit,
    modifier: Modifier,
) {
    OutlinedSurface(
        modifier = modifier,
        onClick = onClick,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SessionHistoryItemHeader(
                item = item,
                isExpanded = isExpanded,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            )
            AnimatedVisibility(visible = isExpanded, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SessionUsageActions(
                        onDetails = onDetails,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}

@Composable
fun SessionHistoryItemHeader(
    item: SessionUsageHistoryItem,
    isExpanded: Boolean,
    modifier: Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.customer_image),
            contentDescription = null,
            modifier = Modifier.size(75.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            Text(
                item.service.info.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            TimeDetails(item.usage)
            Spacer(modifier = Modifier.height(5.dp))
            CustomerDetails(item.customer)
            AnimatedVisibility(
                visible = isExpanded,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Spacer(modifier = Modifier.height(5.dp))
                    EmployeeDetails(item.usage.data.employee)
                    ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                        Phone(item.customer.data.phone)
                    }
                }
            }
        }
    }
}

@Composable
fun SessionUsageActions(
    onDetails: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IconButton(onClick = onDetails) {
            Icon(
                painter = painterResource(id = R.drawable.description),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun TimeDetails(usage: SessionUsage, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.time_clock),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(
            LocalTimeFormatter.current.format(usage.data.date),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun EmployeeDetails(employee: Employee, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.single_employee),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(
            "Испольнитель: ${employee.data.name}",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun CustomerDetails(customer: Customer.ActivatedCustomer, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GenderIcon(
            gender = customer.data.gender,
            modifier = Modifier.size(20.dp),
        )
        Text(
            "Клиент: ${customer.data.name}",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
