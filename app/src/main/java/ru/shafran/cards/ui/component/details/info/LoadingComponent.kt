package ru.shafran.cards.ui.component.details.info

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.CardDescription
import ru.shafran.cards.data.card.CardHistory

class LoadingComponent(private val rawToken: String, private val onLoaded: (Card) -> Unit) : Loading {
    override suspend fun onLoad() {
        delay(5000)
        onLoaded(Card(
            rawToken,
            rawToken[0..2],
            CardDescription.Activated(),
            CardHistory(0, emptyList())
        ))
    }
}

operator fun String.get(indices: IntRange): String {
    return slice(indices)
}