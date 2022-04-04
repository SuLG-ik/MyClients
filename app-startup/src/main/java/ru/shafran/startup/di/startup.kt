package ru.shafran.startup.di

import android.content.Context
import androidx.startup.Initializer
import org.koin.core.KoinApplication
import ru.shafran.startup.logging.NappierInitializer

class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        return context.startDI()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> =
        mutableListOf(NappierInitializer::class.java)
}