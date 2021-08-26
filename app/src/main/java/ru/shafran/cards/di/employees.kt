package ru.shafran.cards.di

import org.koin.dsl.module
import ru.shafran.cards.repository.EmployeesRepository
import ru.shafran.cards.repository.EmployeesRepositoryImpl

val employees = module {
    single<EmployeesRepository> { EmployeesRepositoryImpl(get()) }
}