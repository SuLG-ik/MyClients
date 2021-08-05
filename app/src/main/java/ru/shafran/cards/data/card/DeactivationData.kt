package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class DeactivationData(
    val reason: DeactivationReason = DeactivationReason.MANUAL,
    val note: String? = null,
) : Parcelable