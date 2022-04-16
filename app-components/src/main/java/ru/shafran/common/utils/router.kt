package ru.shafran.common.utils

import com.arkivanov.decompose.router.Router

fun <C : Any, T : Any> Router<C, T>.replaceAll(configuration: C) {
    return navigate(transformer = { listOf(configuration) }, onComplete = { _, _ -> })
}

fun <C : Any, T : Any> Router<C, T>.replaceAll(vararg configuration: C) {
    return navigate(transformer = { configuration.toList() }, onComplete = { _, _ -> })
}