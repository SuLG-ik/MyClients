package ru.shafran.ui.services.picker

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.fade
import ru.shafran.common.services.picker.ConfiguredServicePicker
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.ServiceConfiguration
import ru.shafran.ui.R
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.StaticDropdownMenu

@Composable
internal fun FloatingServicePickerUI(component: ConfiguredServicePicker, modifier: Modifier) {
    Box(modifier = modifier) {
        ServicePickerTitle(
            component.selectedConfiguration.collectAsState().value,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = component::onTogglePicking)
        )
        ServicePickerDropdown(
            component = component,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(300.dp),
        )
    }
}

@Composable
private fun ServicePickerTitle(configuredService: ConfiguredService?, modifier: Modifier) {
    OutlinedSurface(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            if (configuredService == null) {
                EmptyServiceConfiguration(
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            } else {
                ServiceConfiguration(
                    configuration = configuredService.configuration,
                    isSelected = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun EmptyServiceConfiguration(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(Icons.Outlined.Close, contentDescription = null)
        Text(
            stringResource(R.string.customer_session_activation_empty_service),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
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
        )
    }

}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun ServicePickerDropdown(
    component: ConfiguredServicePicker,
    modifier: Modifier = Modifier,
) {
    StaticDropdownMenu(
        expanded = component.isPicking.collectAsState().value,
        onDismissRequest = { component.onTogglePicking(false) },
        modifier = modifier,
    ) {
        Children(
            routerState = component.routerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            animation = childAnimation(fade(tween(250))),
        ) {
            ServicesPickerNavHost(child = it.instance,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun ServicesPickerNavHost(child: ConfiguredServicePicker.Child, modifier: Modifier) {
    when (child) {
        is ConfiguredServicePicker.Child.ConfigurationSelector ->
            ConfigurationSelectorUI(component = child.component, modifier = modifier)
        is ConfiguredServicePicker.Child.ServiceSelector ->
            ServiceSelectorUI(component = child.component, modifier = modifier)
    }
}