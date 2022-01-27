package ru.shafran.network

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*


@Suppress("FunctionName")
internal fun ShafranHttpClient(
    engine: HttpClientEngineFactory<HttpClientEngineConfig>,
    config: NetworkConfig,
    serializer: JsonSerializer,
    logger: Logger = NapierLogger,
): HttpClient {
    Napier.d({ config.toString() }, tag = "ShafranHttpClient")
    return HttpClient(engine) {
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
            val urlBuilder = URLBuilder(config.url)
            url.host = urlBuilder.host
            url.port = urlBuilder.port
            url.protocol = urlBuilder.protocol
            header("api", config.apiVersion)
            contentType(ContentType.Application.Json)
        }
        install(JsonFeature) { this.serializer = serializer }
    }
}

object NapierLogger : Logger {
    override fun log(message: String) {
        Napier.d({message}, tag = "ShafranHttpClient")
    }

}