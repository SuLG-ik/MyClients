package ru.shafran.cards.ui.component.cardsdetails.error

import androidx.annotation.DrawableRes

class CardErrorComponent(
    override val message: String,
    @DrawableRes override val icon: Int,
    private val onReview: () -> Unit,
): CardError {

    override fun onReview() {
        onReview.invoke()
    }

}