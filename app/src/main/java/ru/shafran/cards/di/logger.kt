package ru.shafran.cards.di

import org.koin.dsl.module
import ru.shafran.cards.logger.Logger

val loggerModule = module {
    factory { Logger() }
}