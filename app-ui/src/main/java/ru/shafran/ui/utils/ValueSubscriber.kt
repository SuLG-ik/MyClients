package ru.shafran.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.ValueObserver

@Composable
fun <T : Any> ValueEffect(value: Value<T>, subscription: ValueObserver<T>) {
    val rememberedSubscription = remember { subscription }
    DisposableEffect(key1 = Unit) {
        value.subscribe(rememberedSubscription)
        onDispose {
            value.unsubscribe(subscription)
        }
    }
}