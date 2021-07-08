package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.data.card.Card

class CardInfoLoadedComponent(card: Card): CardInfoLoaded {
    override val currentCard: Value<Card> = MutableValue(card)
}