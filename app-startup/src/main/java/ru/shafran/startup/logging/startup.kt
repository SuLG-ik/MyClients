package ru.shafran.startup.logging

import android.content.Context
import androidx.startup.Initializer
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import ru.shafran.startup.BuildConfig

class NappierInitializer : Initializer<Napier> {

    override fun create(context: Context): Napier {
        return Napier.apply {
            base(
                CrashlyticsAntilog(
                    minimalLogcatLevel = if (BuildConfig.DEBUG) LogLevel.VERBOSE else null,
                    minimalCrashlyticsLevel = if (!BuildConfig.DEBUG) LogLevel.WARNING else null,
                )
            )
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}