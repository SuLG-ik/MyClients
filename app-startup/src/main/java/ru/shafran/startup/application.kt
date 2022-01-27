package ru.shafran.startup

import android.app.Application
import ru.shafran.startup.di.startDI
import ru.shafran.startup.logging.setupLogging

fun Application.setupShafran() {
    startDI()
    setupLogging()
}