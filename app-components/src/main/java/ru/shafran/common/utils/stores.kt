package ru.shafran.common.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.mvikotlin.core.store.Store

internal inline fun <reified T : Store<*, *, *>> ComponentContext.stores() =
    lazy { instanceKeeper.getOrCreate(key = T::class) { StoreHolder(get<T>()) }.store }


internal inline fun <reified T : Store<*, *, *>> ComponentContext.getStore() =
    instanceKeeper.getOrCreate(key = T::class) { StoreHolder(get<T>()) }.store


internal class StoreHolder<T : Store<*, *, *>>(
    val store: T,
) : InstanceKeeper.Instance {
    override fun onDestroy() {
        store.dispose()
    }
}