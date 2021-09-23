package ru.shafran.cards.ui.component.details

import androidx.annotation.DrawableRes
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.cards.data.card.CardModel

sealed class CardDetailsConfiguration : Parcelable {

    @Parcelize
    data class Loading(val message: String): CardDetailsConfiguration()
    @Parcelize
    object Hidden: CardDetailsConfiguration()
    @Parcelize
    data class Info(val card: CardModel): CardDetailsConfiguration()
    @Parcelize
    data class Usage(val card: CardModel): CardDetailsConfiguration()

    @Parcelize
    data class Activation(val card: CardModel): CardDetailsConfiguration()

    @Parcelize
    data class Deactivation(val card: CardModel): CardDetailsConfiguration()

    @Parcelize
    data class Error(val message: String, @DrawableRes val icon: Int): CardDetailsConfiguration()
}
