package ru.shafran.startup.di

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import org.koin.dsl.module
import ru.shafran.network.NetworkConfig
import ru.shafran.startup.BuildConfig

internal val ktorModule = module {
    factory {
        NetworkConfig(
            apiVersion = BuildConfig.NETWORK_API_VERSION,
            url = BuildConfig.NETWORK_API_URL,
            isDebugMode = BuildConfig.DEBUG,
            apiKey = BuildConfig.NETWORK_KEY_CODE,
            authRealm = BuildConfig.NETWORK_API_REALM
        )
    }
    factory<HttpClientEngineFactory<HttpClientEngineConfig>> { CIO }
}