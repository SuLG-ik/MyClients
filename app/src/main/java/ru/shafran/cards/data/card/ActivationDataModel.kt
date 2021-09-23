package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.card.ActivationData

@Parcelize
data class ActivationDataModel(
    val cost: Int,
    val capacity: Int,
    val type: CardTypeModel = CardTypeModel(),
    val note: String = "",
) : Parcelable


fun ActivationData.toModel(): ActivationDataModel {
    return ActivationDataModel(
        cost = cost,
        capacity = capacity,
        type = type.toModel(),
        note = note,
    )
}

fun ActivationDataModel.toData(): ActivationData {
    return ActivationData(
        cost = cost,
        capacity = capacity,
        type = type.toData(),
        note = note,
    )
}