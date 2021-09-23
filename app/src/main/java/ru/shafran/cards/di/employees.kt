package ru.shafran.cards.di

import org.koin.dsl.module
import ru.shafran.network.employee.RemoteEmployeesRepository

val employeesModule = module {
    factory { RemoteEmployeesRepository(get()) }
}