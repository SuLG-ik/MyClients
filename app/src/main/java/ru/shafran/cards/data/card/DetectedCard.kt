package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@JvmInline
@Parcelize
value class DetectedCard(
    val rawToken: String,
) : Parcelable

val EmptyDetectedCard = DetectedCard("")

fun DetectedCard.isEmpty(): Boolean {
    return rawToken.isEmpty()
}
