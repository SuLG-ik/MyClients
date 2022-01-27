package ru.shafran.ui.details.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.shafran.common.details.edit.CustomerActivating
import ru.shafran.common.details.edit.CustomerEditing
import ru.shafran.common.details.edit.CustomerEditor
import ru.shafran.network.Gender
import ru.shafran.network.customers.data.CustomerData
import ru.shafran.ui.R
import ru.shafran.ui.view.GenderSelector
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

@Composable
private fun CustomerEditorUI(component: CustomerEditor, modifier: Modifier) {
    Column(modifier = modifier) {
        val name = rememberSaveable { mutableStateOf(component.data?.name ?: "") }
        val remark = rememberSaveable { mutableStateOf(component.data?.remark ?: "") }
        val gender = rememberSaveable { mutableStateOf(component.data?.gender ?: Gender.UNKNOWN) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false),
                contentAlignment = Alignment.Center,
            ){
                OutlinedTextField(
                    value = name.value,
                    onValueChange = {
                        val trimmedName = it.trim()
                        if (trimmedName.length >= 64) return@OutlinedTextField
                        name.value = trimmedName
                    },
                    label = { Text(stringResource(R.string.customers_name)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
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
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
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