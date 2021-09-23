package ru.shafran.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*


@Suppress("FunctionName")
fun ShafranHttpClient(
    engine: HttpClientEngineFactory<HttpClientEngineConfig>,
    config: NetworkConfig,
    serializer: JsonSerializer,
): HttpClient {
    return HttpClient(engine) {
        developmentMode = config.isDebugMode
        defaultRequest {
            url {
                host = config.host
                protocol = URLProtocol(config.protocol, config.port)
            }
            header("api", config.apiVersion)
        }
        install(JsonFeature) { this.serializer = serializer }
    }
}