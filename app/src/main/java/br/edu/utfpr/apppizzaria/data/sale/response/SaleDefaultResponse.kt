package br.edu.utfpr.apppizzaria.data.sale.response

import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

@Serializable
data class SaleDefaultResponse(
    @Contextual val id: UUID = Utils.GERERIC_UUID,
    val saleItems: List<SaleItemDefaultResponse> = listOf(),
    @Contextual val saleDate: Date = Date(),
    @Contextual val total: BigDecimal = BigDecimal.ZERO
) {
    val pizzasList
        get(): String = "${
            saleItems.map { "${it.quantity} - ${it.pizzaName}" }.joinToString()
        }"
}
