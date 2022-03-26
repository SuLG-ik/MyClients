package ru.shafran.startup.logging

import android.app.Application
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import ru.shafran.startup.BuildConfig

internal fun Application.setupLogging() {
    Napier.base(
        CrashlyticsAntilog(
            minimalLogcatLevel = if (BuildConfig.DEBUG) LogLevel.VERBOSE else null,
            minimalCrashlyticsLevel = if (!BuildConfig.DEBUG) LogLevel.WARNING else null,
        )
    )
}