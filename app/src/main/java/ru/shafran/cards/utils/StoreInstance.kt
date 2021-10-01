package ru.shafran.cards.utils

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.mvikotlin.core.store.Store


fun <T : Store<*, *, *>> InstanceKeeper.getStore(key: Any, factory: () -> T): T =
    getOrCreate(key) { StoreHolder(factory()) }
        .store

inline fun <reified T : Store<*, *, *>> InstanceKeeper.getStore(noinline factory: () -> T): T =
    getStore(T::class, factory)

fun <T : Store<*, *, *>> InstanceKeeper.stores(key: Any, factory: () -> T): Lazy<T> =
    lazy { getStore(key, factory) }

inline fun <reified T : Store<*, *, *>> InstanceKeeper.stores(noinline factory: () -> T) =
    stores(T::class, factory)

fun <T : Store<*, *, *>> InstanceKeeperOwner.getStore(key: Any, factory: () -> T): T =
    instanceKeeper.getOrCreate(key) { StoreHolder(factory()) }
        .store

inline fun <reified T : Store<*, *, *>> InstanceKeeperOwner.getStore(noinline factory: () -> T): T =
    instanceKeeper.getStore(T::class, factory)

fun <T : Store<*, *, *>> InstanceKeeperOwner.stores(key: Any, factory: () -> T): Lazy<T> =
    lazy { instanceKeeper.getStore(key, factory) }

inline fun <reified T : Store<*, *, *>> InstanceKeeperOwner.stores(noinline factory: () -> T) =
    instanceKeeper.stores(T::class, factory)

private class StoreHolder<T : Store<*, *, *>>(
    val store: T
) : InstanceKeeper.Instance {
    override fun onDestroy() {
        store.dispose()
    }
}