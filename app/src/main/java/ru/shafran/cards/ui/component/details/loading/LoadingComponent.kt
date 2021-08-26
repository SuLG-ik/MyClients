package ru.shafran.cards.ui.component.details.loading

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoadingComponent(message: String) : Loading {

    override val message: StateFlow<String> = MutableStateFlow(message)

}