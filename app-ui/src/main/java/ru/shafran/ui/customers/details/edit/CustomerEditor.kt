package ru.shafran.ui.customers.details.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import ru.shafran.common.customers.details.edit.CustomerActivating
import ru.shafran.common.customers.details.edit.CustomerEditing
import ru.shafran.common.customers.details.edit.CustomerEditor
import ru.shafran.common.loading.Loading
import ru.shafran.network.Gender
import ru.shafran.network.PhoneNumber
import ru.shafran.network.customers.data.CustomerData
import ru.shafran.ui.R
import ru.shafran.ui.view.GenderSelector
import ru.shafran.ui.view.OutlinedTextField
import ru.shafran.ui.view.OutlinedTitledDialog
import ru.shafran.ui.view.PhoneInput
import ru.shafran.ui.view.PlaceholderTextField
import ru.shafran.ui.view.Saver


@Composable
internal fun CustomerEditingLoadingUI(component: Loading, modifier: Modifier) {
    OutlinedTitledDialog(
        title = {
            Text(
                stringResource(R.string.customers_activating),
                modifier = Modifier
                    .placeholder(true, highlight = PlaceholderHighlight.fade()),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onBackPressed = {},
        modifier = modifier,
    ) {
        CustomerEditorPlaceholder(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomerEditorPlaceholder(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PlaceholderTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false)
            )
            GenderSelector(
                selectedGender = Gender.UNKNOWN,
                onSelect = {},
                enabled = false,
                modifier = Modifier
                    .size(55.dp)
                    .placeholder(true, highlight = PlaceholderHighlight.fade())
            )
        }
        PlaceholderTextField(modifier = Modifier.fillMaxWidth())
        OutlinedButton(
            enabled = false,
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.customer_apply), modifier = Modifier
                .placeholder(true, highlight = PlaceholderHighlight.fade()))
        }
    }
}

@Composable
internal fun CustomerEditingUI(component: CustomerEditing, modifier: Modifier) {
    OutlinedTitledDialog(
        title = {
            Text(
                stringResource(R.string.customers_editing),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onBackPressed = component::onBack,
        modifier = modifier,
    ) {
        CustomerEditorUI(
            component = component.editor,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
internal fun CustomerActivationUI(component: CustomerActivating, modifier: Modifier) {
    OutlinedTitledDialog(
        title = {
            Text(
                stringResource(R.string.customers_activating),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onBackPressed = component::onBack,
        modifier = modifier,
    ) {
        CustomerEditorUI(
            component = component.editor,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomerEditorUI(component: CustomerEditor, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val name = rememberSaveable { mutableStateOf(component.data?.name ?: "") }
        val remark = rememberSaveable { mutableStateOf(component.data?.remark ?: "") }
        val gender = rememberSaveable { mutableStateOf(component.data?.gender ?: Gender.UNKNOWN) }
        val phone = rememberSaveable(stateSaver = PhoneNumber.Saver) {
            mutableStateOf(component.data?.phone ?: PhoneNumber(""))
        }
        val focus = LocalFocusManager.current
        val keyboard = LocalSoftwareKeyboardController.current
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            OutlinedTextField(
                value = name.value,
                onValueChange = {
                    val trimmedName = it.trim()
                    if (trimmedName.length >= 64) return@OutlinedTextField
                    name.value = trimmedName
                },
                label = { Text(stringResource(R.string.customers_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focus.moveFocus(FocusDirection.Down) },
                    onDone = {
                        focus.clearFocus()
                        keyboard?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false)
            )
            GenderSelector(
                selectedGender = gender.value,
                onSelect = {
                    gender.value = it
                },
                modifier = Modifier.size(55.dp)
            )
        }
        PhoneInput(
            number = phone.value,
            onNumberChange = { phone.value = it },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = remark.value,
            onValueChange = {
                val trimmedName = it.trim()
                if (trimmedName.length >= 250) return@OutlinedTextField
                remark.value = trimmedName
            },
            label = { Text(stringResource(R.string.customers_remark)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) },
                onDone = {
                    focus.clearFocus()
                    keyboard?.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            enabled = name.value.isNotEmpty() && (phone.value.isValid || phone.value.isBlank),
            onClick = {
                component.onEdit(
                    CustomerData(
                        name = name.value,
                        remark = remark.value,
                        gender = gender.value,
                        phone = phone.value,
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.customer_apply))
        }
    }
}

private val PhoneNumber.isValid: Boolean
    get() {
        return number.length == 11
    }

private val PhoneNumber.isBlank: Boolean
    get() {
        return number.length <= 1
    }