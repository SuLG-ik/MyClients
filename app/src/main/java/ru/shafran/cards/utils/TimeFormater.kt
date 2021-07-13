package ru.shafran.cards.utils

import java.time.Month
import java.time.ZonedDateTime

fun ZonedDateTime.format(): String {
    return formatByRelations(ZonedDateTime.now())
}

private fun ZonedDateTime.formatByRelations(other: ZonedDateTime): String {
    return when {
        isToday(other) -> "Сегодня в ${hour}:${minute}"
        isYesterday(other) -> "Вчера в ${hour}:${minute}"
        isThisYear(other) -> "${this.dayOfMonth} ${this.month.format()}"
        else -> "${this.dayOfMonth} ${this.month.format()} $year г."
    }
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