package ru.shafran.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.shafran.network.Gender
import ru.shafran.ui.R

@Composable
internal fun GenderSelector(
    selectedGender: Gender,
    onSelect: (Gender) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isExpanded = rememberSaveable { mutableStateOf(false) }
    OutlinedSurface(
        modifier = modifier
            .clickable { isExpanded.value = true },
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp),
        ) {
            MinifyGender(selectedGender)
            StaticDropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false },
            ) {
                Gender.values().onEach {
                    DropdownMenuItem(
                        onClick = {
                            isExpanded.value = false
                            onSelect(it)
                        },
                    ) {
                        Gender(
                            gender = it,
                            modifier = Modifier.fillMaxWidth(),
                        )
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
    fontStyle: FontStyle? = null,
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
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            stringResource(gender.stringResource),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontStyle = fontStyle,
        )
    }
}

private val Gender.stringResource: Int
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
