package ru.shafran.startup.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ru.shafran.network.NetworkConfig

internal val serializationModule = module {
    factory {
        Json {
            ignoreUnknownKeys = true
            classDiscriminator = "\$type"
            prettyPrint = get<NetworkConfig>().isDebugMode
            encodeDefaults = true
        }
    }
}