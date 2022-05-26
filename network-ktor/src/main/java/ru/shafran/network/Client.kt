package ru.shafran.network

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.shafran.network.auth.AuthenticationStorage
import ru.shafran.network.auth.data.JwtRefreshTokenData
import ru.shafran.network.auth.data.JwtTokenData
import ru.shafran.network.auth.data.RefreshTokenRequest
import ru.shafran.network.auth.data.RefreshTokenResponse
import ru.shafran.network.auth.data.TokenData


@Suppress("FunctionName")
internal fun ShafranHttpClient(
    engine: HttpClientEngineFactory<HttpClientEngineConfig>,
    authStore: AuthenticationStorage,
    config: NetworkConfig,
    serializer: Json,
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
            url(config.url)
            header("api", config.apiVersion)
            header("X-Api-Key", config.apiKey)
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(serializer)
        }
        install(Auth) {
            bearer {
                realm = config.authRealm
                loadTokens {
                    val loadTokens = loadTokens(authStore)
                    loadTokens
                }
                refreshTokens {
                    val tokens = oldTokens ?: return@refreshTokens null
                    val newToken = client.get(
                        "auth/refresh",
                    ) {
                        setBody<RefreshTokenRequest>(
                            RefreshTokenRequest.JwtRefreshTokenRequest(
                                JwtRefreshTokenData(tokens.refreshToken)
                            )
                        )
                    }.body<RefreshTokenResponse>().token
                    authStore.setAuthorizedToken(newToken)
                    newToken.toBearerTokens()
                }
                sendWithoutRequest {
                    !it.url.encodedPath.startsWith("/v1/auth/")
                }
            }
        }
    }
}

private suspend fun loadTokens(authStore: AuthenticationStorage): BearerTokens? {
    val tokens = authStore.getAuthorizedToken()
    return tokens?.toBearerTokens()
}

private fun TokenData.toBearerTokens(): BearerTokens {
    return when (this) {
        is JwtTokenData -> BearerTokens(
            accessToken = accessToken.accessToken,
            refreshToken = refreshToken.refreshToken,
        )
    }
}

internal object NapierLogger : Logger {
    override fun log(message: String) {
        Napier.d({ message }, tag = "ShafranHttpClient")
    }

}

internal suspend inline fun <reified T> tryRequest(request: () -> HttpResponse): T {
    val response = request()
    if (response.status.isSuccess())
        return response.body()
    else
        throw response.body<NetworkException>()
}