package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DetectedCard

interface CardInfo {

    val currentCard: StateFlow<Result<Card>?>
    fun onShowCard(card: DetectedCard)

}