package br.edu.utfpr.apppizzaria.data.sale.response

import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class SaleItemDefaultResponse(
    @Contextual val id: UUID = Utils.GERERIC_UUID,
    @Contextual val pizzaId: UUID = Utils.GERERIC_UUID,
    val pizzaName: String = "",
    val quantity: Long = 0
)
