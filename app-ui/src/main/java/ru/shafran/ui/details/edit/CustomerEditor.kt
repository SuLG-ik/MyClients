package ru.shafran.ui.details.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import ru.shafran.common.details.edit.CustomerActivating
import ru.shafran.common.details.edit.CustomerEditing
import ru.shafran.common.details.edit.CustomerEditor
import ru.shafran.network.Gender
import ru.shafran.network.customers.data.CustomerData
import ru.shafran.ui.R
import ru.shafran.ui.view.GenderSelector
import ru.shafran.ui.view.OutlinedTextField
import ru.shafran.ui.view.TitledDialog

@Composable
internal fun CustomerEditingUI(component: CustomerEditing, modifier: Modifier) {
    TitledDialog(
        title = stringResource(R.string.customers_editing),
        onBackPressed = component.editor::onBack,
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
    TitledDialog(
        title = stringResource(R.string.customers_activating),
        onBackPressed = component.editor::onBack,
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
private fun CustomerEditorUI(component: CustomerEditor, modifier: Modifier) {
    Column(modifier = modifier) {
        val name = rememberSaveable { mutableStateOf(component.data?.name ?: "") }
        val remark = rememberSaveable { mutableStateOf(component.data?.remark ?: "") }
        val gender = rememberSaveable { mutableStateOf(component.data?.gender ?: Gender.UNKNOWN) }

        val focus = LocalFocusManager.current
        val keyboard = LocalSoftwareKeyboardController.current
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
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
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
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
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            enabled = name.value.isNotEmpty(),
            onClick = {
                component.onEdit(
                    CustomerData(
                        name = name.value,
                        remark = remark.value,
                        gender = gender.value,
                        phone = null,
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.customer_apply))
        }
    }
}