package ru.shafran.cards.ui.component.history

import kotlinx.coroutines.flow.Flow
import ru.shafran.cards.data.card.CardActionModel
import ru.shafran.cards.ui.component.cardsdetails.CardDetails

interface History {

    fun onUpdate()

    fun onChooseUsage(activationId: Long)

    val history: Flow<List<CardActionModel>?>

    val cardDetails: CardDetails

}