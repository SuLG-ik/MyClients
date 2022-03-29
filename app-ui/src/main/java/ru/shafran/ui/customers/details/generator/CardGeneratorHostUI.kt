package ru.shafran.ui.customers.details.generator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.customers.details.generator.CardGeneratorHost

@Composable
fun CardGeneratorHostUI(component: CardGeneratorHost, modifier: Modifier) {
    Children(routerState = component.routerState) {
        CardGeneratorNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun CardGeneratorNavHost(child: CardGeneratorHost.Child, modifier: Modifier) {
    when (child) {
        is CardGeneratorHost.Child.Loading ->
            CustomerPlaceholderSenderUI(component = child.component, modifier = modifier)
        is CardGeneratorHost.Child.CardSender ->
            CustomerSenderUI(component = child.component, modifier = modifier)
        is CardGeneratorHost.Child.CardGenerator ->
            CustomerGeneratorUI(component = child.component, modifier = modifier)
    }
}
