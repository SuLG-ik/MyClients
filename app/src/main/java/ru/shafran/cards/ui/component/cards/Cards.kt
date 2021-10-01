package ru.shafran.cards.ui.component.cards

import ru.shafran.cards.ui.component.camera.Camera
import ru.shafran.cards.ui.component.cardsdetails.CardDetails

interface Cards {

    fun onShowCardInfo(cardToken: String)

    val details: CardDetails
    val camera: Camera

}