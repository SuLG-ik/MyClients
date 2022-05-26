package ru.shafran.network

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.shafran.network.auth.AuthenticationStorage
import ru.shafran.network.auth.DataStoreAuthenticationStorage
import ru.shafran.network.datastore.ContextDataStoreFactory
import ru.shafran.network.datastore.DataStoreFactory

val storageModule = module {
    factoryOf(::ContextDataStoreFactory) bind DataStoreFactory::class
    singleOf(::DataStoreAuthenticationStorage) bind AuthenticationStorage::class
}