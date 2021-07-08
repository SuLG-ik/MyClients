package ru.shafran.cards.utils

import com.google.common.util.concurrent.ListenableFuture

fun <T : Any?> ListenableFuture<T>.getOrNull(): T? {
    return if (this.isDone && !this.isCancelled) this.get() else null
}