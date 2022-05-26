package ru.shafran.network.datastore

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import kotlin.reflect.KType

internal class ContextDataStoreFactory(
    private val context: Context,
    private val json: Json,
) : DataStoreFactory {
    override fun <T> createDatastore(
        name: String,
        defaultValue: T,
        type: KType,
        corruptionHandler: ReplaceFileCorruptionHandler<T>?,
        produceMigrations: (Context) -> List<DataMigration<T>>,
        scope: CoroutineScope,
    ): DataStore<T> {
        return androidx.datastore.core.DataStoreFactory.create(
            produceFile = { context.dataStoreFile(name) },
            serializer = kotlinxDataStoreSerializer(
                defaultValue = defaultValue,
                type = type,
                format = json,
            ),
            corruptionHandler = corruptionHandler,
            migrations = produceMigrations.invoke(context),
            scope = scope,
        )
    }
}