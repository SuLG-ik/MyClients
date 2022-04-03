package ru.shafran.ui.services.picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.services.picker.configuration.ConfigurationSelector
import ru.shafran.common.services.picker.configuration.ConfigurationsListSelector
import ru.shafran.network.services.data.Service
import ru.shafran.network.services.data.ServiceConfiguration
import ru.shafran.ui.loading.LoadingUI
import ru.shafran.ui.view.OutlinedTitledDialog

@Composable
internal fun ConfigurationSelectorUI(component: ConfigurationSelector, modifier: Modifier) {
    Children(routerState = component.routerState) {
        ConfigurationSelectorNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
private fun ConfigurationSelectorNavHost(child: ConfigurationSelector.Child, modifier: Modifier) {
    when (child) {
        is ConfigurationSelector.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
        is ConfigurationSelector.Child.ConfigurationsList ->
            ConfigurationsListSelectorUI(child.component, modifier = modifier)
    }
}

@Composable
private fun ConfigurationsListSelectorUI(
    component: ConfigurationsListSelector,
    modifier: Modifier,
) {
    ServiceConfigurationList(
        service = component.service,
        onSelect = component::onSelect,
        onBack = component::onBack,
        selectedConfigurationId = component.selectedConfiguration?.serviceId,
        modifier = modifier,
    )
}


@Composable
private fun ServiceConfigurationList(
    service: Service,
    onSelect: (ServiceConfiguration) -> Unit,
    onBack: () -> Unit,
    selectedConfigurationId: String?,
    modifier: Modifier = Modifier,
) {
    val configurations = service.data.configurations
    OutlinedTitledDialog(

        title = {
            Text(
                service.data.info.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onBackPressed = onBack,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier,
    ) {
        val state = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state),
        ) {
            (configurations).forEach { configuration ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onSelect(configuration)
                    }
                ) {
                    ServiceConfiguration(
                        configuration = configuration,
                        isSelected = selectedConfigurationId == configuration.id,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun ServiceConfiguration(
    configuration: ServiceConfiguration,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
        Text(
            configuration.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth().weight(1f, false)
        )
        Text(
            "${configuration.cost} ₽",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
        )
    }
}