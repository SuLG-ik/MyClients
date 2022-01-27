package ru.shafran.startup.logging

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

internal fun Application.setupLogging() {
    Napier.base(DebugAntilog("ShafranApp"))
}