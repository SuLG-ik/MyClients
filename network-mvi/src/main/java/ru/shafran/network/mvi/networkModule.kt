package ru.shafran.network.mvi

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.shafran.network.auth.ApplicationAuthStore
import ru.shafran.network.auth.ApplicationAuthStoreImpl
import ru.shafran.network.auth.AuthLoginStore
import ru.shafran.network.auth.AuthLoginStoreImpl
import ru.shafran.network.companies.CompaniesListStore
import ru.shafran.network.companies.CompaniesListStoreImpl
import ru.shafran.network.customers.CustomerEditingStore
import ru.shafran.network.customers.CustomerEditingStoreImpl
import ru.shafran.network.customers.CustomerInfoStore
import ru.shafran.network.customers.CustomerInfoStoreImpl
import ru.shafran.network.customers.CustomerSearchStore
import ru.shafran.network.customers.CustomerSearchStoreImpl
import ru.shafran.network.customers.GenerateCustomerStore
import ru.shafran.network.customers.GenerateCustomerStoreImpl
import ru.shafran.network.employees.CreateEmployeeStore
import ru.shafran.network.employees.CreateEmployeeStoreImpl
import ru.shafran.network.employees.EmployeesListStore
import ru.shafran.network.employees.EmployeesListStoreImpl
import ru.shafran.network.employees.SingleEmployeeStore
import ru.shafran.network.employees.SingleEmployeeStoreImpl
import ru.shafran.network.networkModule
import ru.shafran.network.services.CreateServiceConfigurationStore
import ru.shafran.network.services.CreateServiceConfigurationStoreImpl
import ru.shafran.network.services.CreateServiceStore
import ru.shafran.network.services.CreateServiceStoreImpl
import ru.shafran.network.services.EditServiceStore
import ru.shafran.network.services.EditServiceStoreImpl
import ru.shafran.network.services.ServiceInfoStore
import ru.shafran.network.services.ServiceInfoStoreImpl
import ru.shafran.network.services.ServicesListStore
import ru.shafran.network.services.ServicesListStoreImpl
import ru.shafran.network.session.DeactivateSessionStore
import ru.shafran.network.session.DeactivateSessionStoreImpl
import ru.shafran.network.session.SessionActivationStore
import ru.shafran.network.session.SessionActivationStoreImpl
import ru.shafran.network.session.SessionUseStore
import ru.shafran.network.session.SessionUseStoreImpl
import ru.shafran.network.session.SessionsStatsStore
import ru.shafran.network.session.SessionsStatsStoreImpl
import ru.shafran.network.session.SessionsUsageHistoryStore
import ru.shafran.network.session.SessionsUsageHistoryStoreImpl

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
    factoryOf(::CustomerSearchStoreImpl) bind CustomerSearchStore::class
    factoryOf(::SessionsUsageHistoryStoreImpl) bind SessionsUsageHistoryStore::class
    factoryOf(::CreateServiceStoreImpl) bind CreateServiceStore::class
    factoryOf(::EditServiceStoreImpl) bind EditServiceStore::class
    factoryOf(::CreateServiceConfigurationStoreImpl) bind CreateServiceConfigurationStore::class
    factoryOf(::DeactivateSessionStoreImpl) bind DeactivateSessionStore::class
    factoryOf(::CreateEmployeeStoreImpl) bind CreateEmployeeStore::class
    factoryOf(::SessionsStatsStoreImpl) bind SessionsStatsStore::class
    factoryOf(::CompaniesListStoreImpl) bind CompaniesListStore::class
    factoryOf(::ApplicationAuthStoreImpl) bind ApplicationAuthStore::class
    factoryOf(::AuthLoginStoreImpl) bind AuthLoginStore::class
}

val networkModules = module { includes(mviNetworkModule, networkModule) }