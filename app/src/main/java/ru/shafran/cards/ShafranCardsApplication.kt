package ru.shafran.cards

import android.app.Application
import ru.shafran.startup.setupShafran

class ShafranCardsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupShafran()
    }

}