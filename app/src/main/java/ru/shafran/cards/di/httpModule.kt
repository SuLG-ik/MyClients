package ru.shafran.cards.di

import io.ktor.client.engine.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.koin.dsl.module
import ru.shafran.cards.BuildConfig
import ru.shafran.network.NetworkConfig
import ru.shafran.network.ShafranHttpClient

val httpModule = module {
    factory<JsonSerializer> { KotlinxSerializer(get()) }
    factory<HttpClientEngineFactory<HttpClientEngineConfig>> { Android }
    single {
        NetworkConfig(
            isDebugMode = BuildConfig.DEBUG,
            host = BuildConfig.NETWORK_URL,
            apiVersion = BuildConfig.NETWORK_API_VERSION,
            port = BuildConfig.NETWORK_API_PORT,
            protocol = BuildConfig.NETWORK_API_PROTOCOL
        )
    }
    single { ShafranHttpClient(get(), get(), get()) }
}
