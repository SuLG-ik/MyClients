package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.card.Card

@Parcelize
data class CardModel(
    val rawToken: String,
    val id: Long,
    val history: CardHistoryModel = CardHistoryModel(0, emptyList()),
) : Parcelable

fun Card.toModel(): CardModel {
    return CardModel(
        rawToken = rawToken,
        id = id,
        history = history.toModel(),
    )
}

fun CardModel.toData(): Card {
    return Card(
        rawToken = rawToken,
        id = id,
        history = history.toData()
    )
}