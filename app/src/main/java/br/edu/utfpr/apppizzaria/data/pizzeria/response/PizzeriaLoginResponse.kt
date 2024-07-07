package br.edu.utfpr.apppizzaria.data.pizzeria.response

import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PizzeriaLoginResponse(
    @Contextual val id: UUID = Utils.GERERIC_UUID,
    val name: String = ""
)
