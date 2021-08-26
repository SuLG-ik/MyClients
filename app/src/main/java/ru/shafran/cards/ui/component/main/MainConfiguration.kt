package ru.shafran.cards.ui.component.main

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class MainConfiguration : Parcelable {
    @Parcelize
    object Root: MainConfiguration()
}