package ru.shafran.network.utils

import io.ktor.client.plugins.*
import io.ktor.http.*

internal inline fun <T> tryRequest(request: () -> T): T? {
    return try {
        request()
    } catch (e: ResponseException) {
        if (e.response.status == HttpStatusCode.NotFound)
            null
        else
            throw e
    }
}