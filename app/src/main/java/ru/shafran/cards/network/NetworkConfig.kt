package ru.shafran.cards.network

data class NetworkConfig(
    val url: String,
    val timeout: Long,
) {
    fun buildUrl(path: String): String {
        return url + path
    }
}
