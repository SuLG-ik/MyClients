package ru.shafran.startup.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val serializationModule = module {
    factory {
        Json {
            ignoreUnknownKeys = true
            classDiscriminator = "\$type"
            prettyPrint = true
            encodeDefaults = true
        }
    }
}