package ru.shafran.cards

import android.app.Application
import ru.shafran.cards.di.startDI

class ShafranCardsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startDI()
    }

}