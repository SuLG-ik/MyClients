package ru.shafran.startup.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import ru.shafran.network.mvi.networkModules
import ru.shafran.startup.time.timeModule

internal fun Context.startDI(): KoinApplication {
    return startKoin {
        androidContext(this@startDI)
        androidLogger()
        modules(
            serializationModule,
            ktorModule,
            mviModule,
            *networkModules,
            timeModule,
        )
    }
}