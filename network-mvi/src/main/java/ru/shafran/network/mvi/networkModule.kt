package ru.shafran.network.mvi

import org.koin.dsl.module
import ru.shafran.network.employees.EmployeesListStoreFactory
import ru.shafran.network.employees.SingleEmployeeStoreFactory
import ru.shafran.network.networkModule
import ru.shafran.network.services.ServicesListStoreFactory

private val mviNetworkModule = module {
    factory {

    }
    factory { EmployeesListStoreFactory(get()).build() }
    factory { SingleEmployeeStoreFactory(get()).build() }
    factory { ServicesListStoreFactory(get(), get()).build() }
}

val networkModules = arrayOf(mviNetworkModule, networkModule)