package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.card.CardHistory

@Parcelize
data class CardHistoryModel(
    val size: Int,
    val actions: List<CardActionModel>,
) : Parcelable


fun CardHistoryModel.toData(): CardHistory {
    return CardHistory(
        size = size,
        actions = actions.map { it.toData() }
    )
}

fun CardHistory.toModel(): CardHistoryModel {
    return CardHistoryModel(
        size = size,
        actions = actions.map { it.toModel() }
    )
}