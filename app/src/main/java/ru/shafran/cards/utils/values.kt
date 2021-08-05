package ru.shafran.cards.utils

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

fun <T : Any> Value<T>.asStateFlow(): StateFlow<T> {
    val flow = MutableStateFlow(value)
    subscribe { flow.value = it }
    return flow.asStateFlow()
}