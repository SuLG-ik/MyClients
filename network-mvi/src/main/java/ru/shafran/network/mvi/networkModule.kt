package ru.shafran.network.mvi

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.shafran.network.customers.CustomerEditingStore
import ru.shafran.network.customers.CustomerEditingStoreImpl
import ru.shafran.network.customers.CustomerInfoStore
import ru.shafran.network.customers.CustomerInfoStoreImpl
import ru.shafran.network.customers.GenerateCustomerStore
import ru.shafran.network.customers.GenerateCustomerStoreImpl
import ru.shafran.network.customers.SessionActivationStore
import ru.shafran.network.customers.SessionActivationStoreImpl
import ru.shafran.network.employees.EmployeesListStore
import ru.shafran.network.employees.EmployeesListStoreImpl
import ru.shafran.network.employees.SingleEmployeeStore
import ru.shafran.network.employees.SingleEmployeeStoreImpl
import ru.shafran.network.networkModule
import ru.shafran.network.services.ServiceInfoStore
import ru.shafran.network.services.ServiceInfoStoreImpl
import ru.shafran.network.services.ServicesListStore
import ru.shafran.network.services.ServicesListStoreImpl
import ru.shafran.network.session.SessionUseStore
import ru.shafran.network.session.SessionUseStoreImpl

private val mviNetworkModule = module {
    factory { Dispatchers.Main } bind CoroutineDispatcher::class
    factoryOf(::CustomerInfoStoreImpl) bind CustomerInfoStore::class
    factoryOf(::SessionActivationStoreImpl) bind SessionActivationStore::class
    factoryOf(::SingleEmployeeStoreImpl) bind SingleEmployeeStore::class
    factoryOf(::ServicesListStoreImpl) bind ServicesListStore::class
    factoryOf(::EmployeesListStoreImpl) bind EmployeesListStore::class
    factoryOf(::CustomerEditingStoreImpl) bind CustomerEditingStore::class
    factoryOf(::SessionUseStoreImpl) bind SessionUseStore::class
    factoryOf(::GenerateCustomerStoreImpl) bind GenerateCustomerStore::class
    factoryOf(::ServiceInfoStoreImpl) bind ServiceInfoStore::class
}

val networkModules = arrayOf(mviNetworkModule, networkModule)