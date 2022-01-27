package ru.shafran.startup.di

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.koin.dsl.module
import ru.shafran.network.NetworkConfig
import ru.shafran.startup.BuildConfig

internal val ktorModule = module {
    factory {
        NetworkConfig(
            apiVersion = BuildConfig.NETWORK_API_VERSION,
            url = BuildConfig.NETWORK_API_URL,
            isDebugMode = BuildConfig.DEBUG,
        )
    }
    factory<JsonSerializer> { KotlinxSerializer(get()) }
    factory<HttpClientEngineFactory<HttpClientEngineConfig>> { CIO }
}