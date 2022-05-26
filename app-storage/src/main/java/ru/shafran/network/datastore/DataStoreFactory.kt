package ru.shafran.network.datastore

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface DataStoreFactory {

    fun <T> createDatastore(
        name: String,
        defaultValue: T,
        type: KType,
        corruptionHandler: ReplaceFileCorruptionHandler<T>? = null,
        produceMigrations: (Context) -> List<DataMigration<T>> = { listOf() },
        scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    ): DataStore<T>

}

inline fun <reified T> DataStoreFactory.createDatastore(
    name: String,
    defaultValue: T,
    corruptionHandler: ReplaceFileCorruptionHandler<T>? = null,
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
) = createDatastore(
    name = name,
    defaultValue = defaultValue,
    type = typeOf<T>(),
    corruptionHandler = corruptionHandler,
    scope = scope,
)