package ru.shafran.startup.time

import org.koin.dsl.module
import ru.shafran.ui.utils.TimeFormatter

val timeModule = module {
    factory<TimeFormatter> { RussianTimeFormatter() }
}