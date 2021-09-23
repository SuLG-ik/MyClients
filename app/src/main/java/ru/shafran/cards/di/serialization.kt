package ru.shafran.cards.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module

val serializationModule = module {
    factory {
        Json {
            ignoreUnknownKeys = true
            classDiscriminator = "\$type"
        }
    }
}