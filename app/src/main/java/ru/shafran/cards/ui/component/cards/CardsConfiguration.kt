package ru.shafran.cards.ui.component.cards

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class CardsConfiguration: Parcelable {

    @Parcelize
    object UsagesList: CardsConfiguration()

    @Parcelize
    object Camera: CardsConfiguration()

}