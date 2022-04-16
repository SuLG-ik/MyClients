package ru.shafran.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
internal inline fun <reified T : Enum<T>> FloatingEnumSelector(
    value: T,
    crossinline onValueChanged: (T) -> Unit,
    crossinline title: @Composable RowScope.() -> Unit,
    crossinline iconFactory: (T) -> Int,
    crossinline labelFactory: (T) -> Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val isExpanded = rememberSaveable { mutableStateOf(false) }
    OutlinedSurface(
        onClick = { if (enabled) isExpanded.value = true },
        modifier = Modifier
            .size(55.dp)
            .then(modifier),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            MinifyEnum(iconFactory(value))
            if (enabled)
                StaticDropdownMenu(
                    expanded = isExpanded.value,
                    onDismissRequest = { isExpanded.value = false },
                ) {
                    OutlinedTitledDialog(
                        title = {
                            ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                                title()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.50f)
                            .padding(10.dp),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        ProvideTextStyle(value = MaterialTheme.typography.bodySmall) {
                            enumValues<T>().onEach {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            isExpanded.value = false
                                            onValueChanged(it)
                                        }
                                ) {
                                    EnumItem(
                                        icon = iconFactory(it),
                                        title = labelFactory(it),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }
}

@Composable
internal fun MinifyEnum(
    icon: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
    }
}


@Composable
internal fun EnumItem(
    icon: Int,
    title: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
        Text(
            stringResource(title),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = style,
        )
    }
}

