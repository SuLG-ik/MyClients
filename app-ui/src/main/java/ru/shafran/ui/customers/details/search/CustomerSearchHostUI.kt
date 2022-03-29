package ru.shafran.ui.customers.details.search

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.common.customers.details.search.CustomerSearchHost

@Composable
fun CustomerSearchHostUI(component: CustomerSearchHost, modifier: Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Поиск карточки", style = MaterialTheme.typography.headlineSmall)
        Crossfade(component.currentOption.subscribeAsState().value) {
            when (it) {
                CustomerSearchHost.SearchOption.PHONE -> {
                    CustomerSearchByPhoneUI(component.searchByPhone,
                        modifier = Modifier.fillMaxWidth())
                }
                CustomerSearchHost.SearchOption.NAME -> TODO()
            }
        }
    }
}
