package br.edu.utfpr.apppizzaria.data.user.local

import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import java.util.UUID

data class UserLogged(
    val id: UUID = Utils.GERERIC_UUID,
    val name: String = "",
    val userType: UserType = UserType.PIZZERIA
)