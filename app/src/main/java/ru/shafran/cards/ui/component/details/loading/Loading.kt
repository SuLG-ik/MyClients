package ru.shafran.cards.ui.component.details.loading

import kotlinx.coroutines.flow.StateFlow

interface Loading {

    val message: StateFlow<String>

}