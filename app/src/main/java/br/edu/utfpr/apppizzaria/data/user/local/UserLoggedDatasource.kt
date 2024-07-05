package br.edu.utfpr.apppizzaria.data.user.local

import android.content.Context
import kotlinx.coroutines.flow.first
import java.util.UUID

class UserLoggedDatasource(
    private val context: Context
) {
    suspend fun getUserLogged(): UserLogged? {
        return context.userLoggedDataStore.data.first().let {
            if (it.id.isNotBlank()) {
                return UserLogged(
                    id = UUID.fromString(it.id),
                    name = it.name,
                    userType = UserType.valueOf(it.userType)
                )
            }
            null
        }
    }

    suspend fun saveUserLogged(user: UserLogged) {
        context.userLoggedDataStore.updateData { currentUserLogged ->
            currentUserLogged.toBuilder()
                .setId(user.id.toString())
                .setName(user.name)
                .setUserType(user.userType.toString())
                .build()
        }
    }

    suspend fun clearUserLogged() {
        context.userLoggedDataStore.updateData {
            it.toBuilder()
                .clearId()
                .clearName()
                .clearUserType()
                .build()
        }
    }
}