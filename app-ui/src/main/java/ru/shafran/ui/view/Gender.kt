package ru.shafran.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import ru.shafran.network.Gender
import ru.shafran.ui.R

@Composable
internal fun FloatingGenderSelector(
    selectedGender: Gender,
    onSelect: (Gender) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val isExpanded = rememberSaveable { mutableStateOf(false) }
    OutlinedSurface(
        onClick = { if (enabled) isExpanded.value = true },
        modifier = Modifier.size(55.dp).then(modifier),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            MinifyGender(selectedGender)
            if (enabled)
                StaticDropdownMenu(
                    expanded = isExpanded.value,
                    onDismissRequest = { isExpanded.value = false },
                ) {
                    OutlinedTitledDialog(
                        title = {
                            Text(
                                stringResource(R.string.gender_selector_title),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.50f)
                            .padding(10.dp),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Gender.values().onEach {
                            Surface(
                                modifier = Modifier.clickable {
                                    isExpanded.value = false
                                    onSelect(it)
                                }
                            ) {
                                Gender(
                                    gender = it,
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

@Composable
internal fun MinifyGender(
    gender: Gender,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = gender.iconResource),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
    }
}


@Composable
internal fun Gender(
    gender: Gender,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        GenderIcon(
            gender,
            Modifier.size(20.dp)
        )
        Text(
            stringResource(gender.stringResource),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = style,
        )
    }
}

@Composable
internal fun GenderIcon(
    gender: Gender,
    modifier: Modifier = Modifier,
) {
    Icon(
        painterResource(id = gender.iconResource),
        contentDescription = null,
        modifier = modifier.defaultMinSize(20.dp),
    )
}

val Gender.stringResource: Int
    @StringRes
    get() {
        return when (this) {
            Gender.MALE -> R.string.customer_gender_male
            Gender.FEMALE -> R.string.customer_gender_female
            Gender.UNKNOWN -> R.string.customer_gender_unknown
        }
    }

private val Gender.iconResource
    @DrawableRes
    get() = when (this) {
        Gender.MALE -> R.drawable.male
        Gender.FEMALE -> R.drawable.female
        else -> R.drawable.unknown_gender
    }
