package ru.shafran.cards.di

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ru.shafran.cards.BuildConfig
import ru.shafran.cards.network.KtorShafranNetworkDao
import ru.shafran.cards.network.NetworkConfig
import ru.shafran.cards.network.ShafranNetworkDao
import ru.shafran.cards.repository.CardsRepository
import ru.shafran.cards.repository.CardsRepositoryImpl

val cardsModule = module {
    single { Json {
        ignoreUnknownKeys = true
        classDiscriminator = "\$type"
    } }
    factory<JsonSerializer> { KotlinxSerializer(get()) }
    single {
        HttpClient(Android) {
            install(JsonFeature) {
                serializer = get()
            }
        }
    }
    factory {
        NetworkConfig(
            url = BuildConfig.NETWORK_URL,
        )
    }
    factory<ShafranNetworkDao> { KtorShafranNetworkDao(get(), get()) }
    single<CardsRepository> { CardsRepositoryImpl(get()) }
}