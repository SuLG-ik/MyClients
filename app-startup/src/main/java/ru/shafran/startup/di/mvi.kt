package ru.shafran.startup.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module

internal val mviModule = module {
    single<StoreFactory> { LoggingStoreFactory(DefaultStoreFactory()) }
}