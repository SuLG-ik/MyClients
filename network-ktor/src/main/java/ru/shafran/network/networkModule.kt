package ru.shafran.network

import io.ktor.client.*
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
    single<HttpClient>{ ShafranHttpClient(get(), get(), get()) }
    single<ServicesRepository> { KtorServicesRepository(get()) }
    single<SessionsRepository> { KtorSessionsRepository(get()) }
    single<CustomersRepository> { KtorCustomersRepository(get()) }
    single<EmployeesRepository> { KtorEmployeesRepository(get(),get()) }
}