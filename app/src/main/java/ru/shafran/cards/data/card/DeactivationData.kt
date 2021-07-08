package ru.shafran.cards.data.card

import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
class DeactivationData(val reason: DeactivationReason = DeactivationReason.MANUAL, val note: String? = null): Parcelable