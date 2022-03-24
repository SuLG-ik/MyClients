package ru.shafran.startup.logging

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel

class CrashlyticsAntilog(private val minimalLevel: LogLevel) : Antilog() {
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    ) {
        if (priority >= minimalLevel && throwable != null) {
            if (message != null) {
                Firebase.crashlytics.log(message)
            }
            Firebase.crashlytics.recordException(throwable)
        }
    }
}