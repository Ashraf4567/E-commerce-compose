package com.example.e_commerce_compose.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.e_commerce_compose.data.model.UserCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserSerializer: Serializer<UserCredentials> {
    override val defaultValue: UserCredentials
        get() = UserCredentials("" , "", "" , "")

    override suspend fun readFrom(input: InputStream): UserCredentials {
        return try {
            Json.decodeFromString(
                UserCredentials.serializer() , input.readBytes().decodeToString()
            )
        }catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }
    }

    override suspend fun writeTo(t: UserCredentials, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(UserCredentials.serializer(), t)
                    .encodeToByteArray()
            )
        }
    }
}