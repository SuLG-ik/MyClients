package ru.shafran.network

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


@Suppress("FunctionName")
internal fun ShafranHttpClient(
    engine: HttpClientEngineFactory<HttpClientEngineConfig>,
    config: NetworkConfig,
    serializer: Json,
    logger: Logger = NapierLogger,
): HttpClient {
    Napier.d({ config.toString() }, tag = "ShafranHttpClient")
    return HttpClient(engine) {
        engine {

        }
        developmentMode = config.isDebugMode
        install(Logging) {
            this.logger = logger
            if (config.isDebugMode) {
                this.level = LogLevel.ALL
            } else {
                this.level = LogLevel.INFO
            }
        }
        defaultRequest {

            url(config.url)
            header("api", config.apiVersion)
            header("X-Api-Key", config.apiKey)
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(serializer)
        }
    }
}

internal object NapierLogger : Logger {
    override fun log(message: String) {
        Napier.d({ message }, tag = "ShafranHttpClient")
    }

}

internal suspend inline fun <T> tryRequest(request: () -> T): T {
    return try {
        request()
    } catch (e: ResponseException) {
        throw runCatching { e.response.call.body<ShafranNetworkException>() }
            .getOrElse { e }
    }
}