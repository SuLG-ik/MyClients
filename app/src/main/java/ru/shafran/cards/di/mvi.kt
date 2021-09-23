package ru.shafran.cards.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module
import ru.shafran.cards.BuildConfig

val mviCoreModule = module {
    if (BuildConfig.DEBUG) {
        factory<StoreFactory> { LoggingStoreFactory(DefaultStoreFactory) }
    }else {
        factory<StoreFactory> { DefaultStoreFactory }
    }
}