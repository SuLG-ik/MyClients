package ru.shafran.ui.customers.details.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.customers.details.search.CustomerSearchByPhone
import ru.shafran.common.customers.details.search.EmptyInput
import ru.shafran.common.customers.details.search.FoundCustomerChild
import ru.shafran.common.error.Error
import ru.shafran.common.loading.Loading
import ru.shafran.network.PhoneNumber
import ru.shafran.ui.R
import ru.shafran.ui.customers.details.info.PlaceholderCustomerInfo
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.PhoneInput
import ru.shafran.ui.view.Saver

private val PhoneNumber.isValid: Boolean
    get() {
        return number.length == 11
    }

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomerSearchByPhoneUI(component: CustomerSearchByPhone, modifier: Modifier) {
    val keyboard = LocalSoftwareKeyboardController.current
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        val phone =
            rememberSaveable(stateSaver = PhoneNumber.Saver) { mutableStateOf(PhoneNumber("")) }
        PhoneInput(
            number = phone.value,
            onNumberChange = { phone.value = it },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedButton(
            enabled = phone.value.isValid,
            onClick = {
                component.onSearch(phone.value)
                keyboard?.hide()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поиск")
        }
        Children(component.routerState) {
            CustomerSearchByPhoneNavHost(
                child = it.instance,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun CustomerSearchByPhoneNavHost(child: FoundCustomerChild, modifier: Modifier) {
    when (child) {
        is FoundCustomerChild.CustomersList ->
            FoundCustomersListUI(component = child.component, modifier = modifier)
        is FoundCustomerChild.EmptyInput ->
            EmptyInputUI(component = child.component, modifier)
        is FoundCustomerChild.Error ->
            CustomerSearchErrorUI(component = child.component, modifier = modifier)
        is FoundCustomerChild.Loading ->
            CustomerSearchLoadingUI(component = child.component, modifier = modifier)
    }
}

@Composable
fun CustomerSearchLoadingUI(component: Loading, modifier: Modifier) {
    OutlinedSurface(
        modifier = modifier
    ) {
        PlaceholderCustomerInfo(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
    }
}

@Composable
fun CustomerSearchErrorUI(component: Error, modifier: Modifier) {
    OutlinedSurface(
        modifier = modifier
    ) {
        ErrorUI(component = component, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
    }
}

@Composable
fun EmptyInputUI(component: EmptyInput, modifier: Modifier) {
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
            Text("Начните поиск")
        }
    }
}