package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.value.Value
import ru.shafran.cards.data.card.Card

interface CardInfoLoaded {
    val currentCard: Value<Card>
}