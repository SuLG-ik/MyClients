package ru.shafran.cards.ui.component.details.info

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.*

interface CardInfo {

    val currentCard: StateFlow<Result<Card>?>
    fun onShowCard(card: DetectedCard)
    fun useCardById(id: Int, data: UsageData)
    fun activateCardById(id: Int, data: ActivationData)
    fun deactivateCardById(id: Int, data: DeactivationData)
}