package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.card.CardType

@Parcelize
data class CardTypeModel(
    val type: String = "Другое",
    val icon: String? = null,
) : Parcelable

fun CardType.toModel(): CardTypeModel {
    return CardTypeModel(
        type = type,
        icon = icon,
    )
}

fun CardTypeModel.toData(): CardType {
    return CardType(
        type = type,
        icon = icon,
    )
}