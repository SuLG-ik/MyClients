package ru.shafran.cards.ui.component.loading

import kotlinx.coroutines.flow.StateFlow

interface Loading {

    val message: StateFlow<String>

}