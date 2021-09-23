package ru.shafran.cards.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

fun Application.startDI() {
    startKoin {
        androidContext(this@startDI)
        androidLogger()
        modules(
            loggerModule,

            serializationModule,
            httpModule,

            recognizingModule,

            mviCoreModule,
            cardsModule,
            employeesModule,
        )
    }
}