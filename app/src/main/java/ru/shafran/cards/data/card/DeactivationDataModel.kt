package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.card.DeactivationData

@Parcelize
class DeactivationDataModel(val note: String = "") : Parcelable

fun DeactivationData.toModel(): DeactivationDataModel {
    return DeactivationDataModel(
        note = note,
    )
}
fun DeactivationDataModel.toData(): DeactivationData {
    return DeactivationData(
        note = note,
    )
}