package br.edu.utfpr.apppizzaria.data.pizzeria.response

import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PizzeriaSaveResponse(
    @Contextual val id: UUID = Utils.GERERIC_UUID,
    val name: String = "",
    val phone: String = "",
    val email: String = ""
)
