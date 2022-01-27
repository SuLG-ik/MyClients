package ru.shafran.startup.time

import ru.shafran.ui.utils.TimeFormatter
import java.time.Month
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class RussianTimeFormatter : TimeFormatter {
    override fun format(time: ZonedDateTime): String {
        return time.formatByRelations(ZonedDateTime.now())
    }


    private val FORMATTER = DateTimeFormatterBuilder()
        .appendValue(ChronoField.HOUR_OF_DAY, 2)
        .appendLiteral(':')
        .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
        .toFormatter()

    private fun ZonedDateTime.formatByRelations(other: ZonedDateTime): String {
        return when {
            isToday(other) -> "Сегодня в ${toDoubleTime()}"
            isYesterday(other) -> "Вчера в ${toDoubleTime()}"
            isThisYear(other) -> "${this.dayOfMonth} ${this.month.format()} в ${toDoubleTime()}"
            else -> "${this.dayOfMonth} ${this.month.format()} $year г. в ${toDoubleTime()}"
        }
    }

    private fun ZonedDateTime.toDoubleTime(): String {
        return this.format(FORMATTER)
    }

    private fun ZonedDateTime.isToday(other: ZonedDateTime): Boolean {
        return dayOfYear == other.dayOfYear && year == other.year
    }

    private fun ZonedDateTime.isYesterday(other: ZonedDateTime): Boolean {
        val thiss = plusDays(1)
        return thiss.dayOfYear == other.dayOfYear && thiss.year == other.year
    }

    private fun ZonedDateTime.isThisYear(other: ZonedDateTime): Boolean {
        return year == other.year
    }

    private fun Month.format(): String {
        return when (this) {
            Month.JANUARY -> "Янв."
            Month.FEBRUARY -> "Фев."
            Month.MARCH -> "Мрт."
            Month.APRIL -> "Апр."
            Month.MAY -> "Мая"
            Month.JUNE -> "Июн."
            Month.JULY -> "Июл."
            Month.AUGUST -> "Авг.   "
            Month.SEPTEMBER -> "Сен."
            Month.OCTOBER -> "Окт."
            Month.NOVEMBER -> "Нбр."
            Month.DECEMBER -> "Дек."
        }
    }
}