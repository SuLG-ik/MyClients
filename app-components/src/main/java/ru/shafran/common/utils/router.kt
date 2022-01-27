package ru.shafran.common.utils

import com.arkivanov.decompose.router.Router

fun <C : Any, T : Any> Router<C, T>.replaceAll(configuration: C) {
    return navigate { listOf(configuration) }
}

fun <C : Any, T : Any> Router<C, T>.replaceAll(vararg configuration: C, ) {
    return navigate { configuration.toList() }
}