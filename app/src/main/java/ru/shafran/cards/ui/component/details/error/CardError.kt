package ru.shafran.cards.ui.component.details.error

import kotlinx.coroutines.flow.StateFlow

interface CardError {

    val message: StateFlow<String>
    val icon: StateFlow<Int>


    fun onReview()
}