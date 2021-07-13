package ru.shafran.cards.repository

import kotlinx.coroutines.flow.Flow
import ru.shafran.cards.data.card.Card

interface CardsRepository {

    suspend fun getCardByToken(token: String): Card

}