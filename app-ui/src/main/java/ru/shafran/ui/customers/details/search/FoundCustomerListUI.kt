package ru.shafran.ui.customers.details.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.common.customers.details.search.FoundCustomersList
import ru.shafran.network.customers.data.FoundCustomerItem
import ru.shafran.ui.R
import ru.shafran.ui.customers.details.info.CustomerInfo
import ru.shafran.ui.customers.details.info.SessionItemHeader
import ru.shafran.ui.view.MaterialDivider
import ru.shafran.ui.view.OutlinedSurface

@Composable
fun FoundCustomersListUI(component: FoundCustomersList, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (component.customers.isEmpty()) {
            EmptyCustomersList(modifier = Modifier.fillMaxWidth())
        } else {
            component.customers.forEach {
                FoundCustomerItemUI(
                    item = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { component.onSelect(it) }
                )
            }
        }
    }
}

@Composable
fun EmptyCustomersList(modifier: Modifier) {
    OutlinedSurface(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(painterResource(id = R.drawable.empty_history), contentDescription = null)
            Text("Карточка не найдена")
        }
    }
}

@Composable
fun FoundCustomerItemUI(item: FoundCustomerItem, modifier: Modifier) {
    OutlinedSurface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            CustomerInfo(
                customer = item.customer.data,
                modifier = Modifier.fillMaxWidth()
            )
            val lastUsedSession = item.lastUsedSession
            if (lastUsedSession != null) {
                MaterialDivider(modifier = Modifier.fillMaxWidth())
                Text("Предыдущий сеанс: ", style = MaterialTheme.typography.titleLarge)
                SessionItemHeader(
                    session = lastUsedSession,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

    }
}