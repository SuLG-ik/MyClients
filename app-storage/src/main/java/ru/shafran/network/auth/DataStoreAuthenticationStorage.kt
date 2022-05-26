package ru.shafran.network.auth

import kotlinx.coroutines.flow.first
import ru.shafran.network.auth.data.TokenData
import ru.shafran.network.datastore.DataStoreFactory
import ru.shafran.network.datastore.createDatastore

class DataStoreAuthenticationStorage(factory: DataStoreFactory) : AuthenticationStorage {

    private val tokenStore = factory.createDatastore<TokenData?>("token", defaultValue = null)

    override suspend fun getAuthorizedToken(): TokenData? {
        return tokenStore.data.first()
    }

    override suspend fun setAuthorizedToken(token: TokenData) {
        tokenStore.updateData { token }
    }


}