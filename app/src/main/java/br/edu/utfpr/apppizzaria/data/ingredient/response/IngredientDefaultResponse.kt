package br.edu.utfpr.apppizzaria.data.ingredient.response

import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

@Serializable
data class IngredientDefaultResponse(
    @Contextual val id: UUID = Utils.GERERIC_UUID,
    val name: String = "",
    val description: String = "",
    @Contextual val price: BigDecimal = BigDecimal.ZERO,
    val measurementUnit: MeasurementUnit = MeasurementUnit.UN,
    @Contextual val quantity: BigDecimal = BigDecimal.ZERO
) {
    val stockDescription get(): String = "$quantity $measurementUnit"
}
