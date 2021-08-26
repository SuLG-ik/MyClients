package ru.shafran.cards.ui.component.details.error

import androidx.annotation.DrawableRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CardErrorComponent(message: String, @DrawableRes icon: Int, private val onReview: () -> Unit): CardError {

    override val message: StateFlow<String> = MutableStateFlow(message)
    override val icon: StateFlow<Int> = MutableStateFlow(icon)

    override fun onReview() {
        onReview.invoke()
    }

}