package ru.shafran.cards.data.card

import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize

@JvmInline
@Parcelize
value class DetectedCard(
    val rawToken: String,
) : Parcelable

val EmptyDetectedCard = DetectedCard("")

fun DetectedCard.isEmpty(): Boolean {
    return rawToken.isEmpty()
}
