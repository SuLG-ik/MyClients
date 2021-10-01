package ru.shafran.cards.ui.component.cardsdetails.error

interface CardError {

    val message: String
    val icon: Int

    fun onReview()
}