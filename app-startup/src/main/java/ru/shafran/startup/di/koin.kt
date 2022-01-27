package ru.shafran.startup.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.shafran.network.mvi.networkModules
import ru.shafran.startup.time.timeModule

internal fun Application.startDI() {
    startKoin {
        androidContext(this@startDI)
//        androidLogger()
        modules(
            serializationModule,
            ktorModule,
            mviModule,
            *networkModules,
            timeModule,
        )
    }
}