package ru.shafran.cards.di

import org.koin.dsl.module
import ru.shafran.network.card.RemoteCardsRepository

val cardsModule = module {
    single { RemoteCardsRepository(get()) }
}