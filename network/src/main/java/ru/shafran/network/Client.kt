package ru.shafran.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import java.util.logging.Logger


@Suppress("FunctionName")
fun ShafranHttpClient(
    engine: HttpClientEngineFactory<HttpClientEngineConfig>,
    config: NetworkConfig,
    serializer: JsonSerializer,
): HttpClient {
    Logger.getLogger("SosiBibu").info(config.toString())
    return HttpClient(engine) {
        developmentMode = config.isDebugMode
        defaultRequest {
            url {
                host = config.host
                port = config.port
            }
//            header("api", config.apiVersion)
        }
        install(JsonFeature) { this.serializer = serializer }
    }
}