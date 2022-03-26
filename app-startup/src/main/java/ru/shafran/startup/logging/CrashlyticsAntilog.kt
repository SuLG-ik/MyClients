package ru.shafran.startup.logging

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel

class CrashlyticsAntilog(
    private val minimalLogcatLevel: LogLevel?,
    private val minimalCrashlyticsLevel: LogLevel?,
) : Antilog() {
    private val antilog = DebugAntilog()

    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    ) {
        if (minimalLogcatLevel != null && priority >= minimalLogcatLevel)
            antilog.log(priority, tag, throwable, message)
        if (minimalCrashlyticsLevel != null && priority >= minimalCrashlyticsLevel && throwable != null) {
            if (message != null) {
                Firebase.crashlytics.log(message)
            }
            Firebase.crashlytics.recordException(throwable)
        }
    }
}