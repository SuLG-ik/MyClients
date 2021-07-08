package ru.shafran.cards.ui.component.main

import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize

sealed class MainConfiguration : Parcelable {
    @Parcelize
    object Splash : MainConfiguration()

    @Parcelize
    object Camera : MainConfiguration()
}