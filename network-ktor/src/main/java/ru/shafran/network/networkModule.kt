package ru.shafran.network

import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.shafran.network.customers.CustomersRepository
import ru.shafran.network.customers.KtorCustomersRepository
import ru.shafran.network.employees.EmployeesRepository
import ru.shafran.network.employees.KtorEmployeesRepository
import ru.shafran.network.services.KtorServicesRepository
import ru.shafran.network.services.ServicesRepository
import ru.shafran.network.session.SessionsRepository
import ru.shafran.network.sessions.KtorSessionsRepository

val networkModule = module {
    factory { NapierLogger } bind Logger::class
    singleOf(::ShafranHttpClient) bind HttpClient::class
    singleOf(::KtorServicesRepository) bind ServicesRepository::class
    singleOf(::KtorSessionsRepository) bind SessionsRepository::class
    singleOf(::KtorCustomersRepository) bind CustomersRepository::class
    singleOf(::KtorEmployeesRepository) bind EmployeesRepository::class
}