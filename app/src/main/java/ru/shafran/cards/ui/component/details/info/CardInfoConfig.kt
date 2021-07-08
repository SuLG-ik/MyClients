package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import ru.shafran.cards.data.card.Card

sealed class CardInfoConfig : Parcelable {
    @Parcelize
    data class Loading(val rawToken: String) : CardInfoConfig()

    @Parcelize
    data class Success(val card: Card) : CardInfoConfig()
}
