package br.edu.utfpr.apppizzaria.data.user.local

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import br.edu.utfpr.apppizzaria.data.proto.User
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserSerializer : Serializer<User> {
    override val defaultValue: User
        get() = User.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): User {
        try {
            return User.parseFrom(input)
        } catch (ex: InvalidProtocolBufferException) {
            throw CorruptionException("Não foi possível ler o arquivo proto", ex)
        }
    }

    override suspend fun writeTo(user: User, output: OutputStream) = user.writeTo(output)
}

val Context.userLoggedDataStore: DataStore<User> by dataStore(
    fileName = "user.pb",
    serializer = UserSerializer
)