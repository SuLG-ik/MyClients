package ru.shafran.ui.utils

import androidx.compose.runtime.compositionLocalOf
import java.time.ZonedDateTime

interface TimeFormatter {

    fun format(time: ZonedDateTime): String

}

val LocalTimeFormatter = compositionLocalOf<TimeFormatter> { error("TimeFormatter does not exist in this composition context") }