package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.card.UsageData

@Parcelize
data class UsageDataModel(val employeeId: Long? = null, val note: String = "") : Parcelable

fun UsageData.toModel(): UsageDataModel {
    return UsageDataModel(
        note = note,
        employeeId = employeeId,
    )
}

fun UsageDataModel.toData(): UsageData {
    return UsageData(
        note = note,
        employeeId = employeeId,
    )
}