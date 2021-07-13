package ru.shafran.cards.network

data class NetworkConfig(
    val url: String,
) {
    fun buildUrl(path: String): String {
        return url + path
    }
}
