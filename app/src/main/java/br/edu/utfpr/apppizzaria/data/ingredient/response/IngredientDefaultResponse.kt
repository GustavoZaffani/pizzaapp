package br.edu.utfpr.apppizzaria.data.ingredient.response

import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

@Serializable
data class IngredientDefaultResponse(
    @Contextual val id: UUID,
    val name: String = "",
    val description: String = "",
    @Contextual val price: BigDecimal = BigDecimal.ZERO,
    val measurementUnit: MeasurementUnit = MeasurementUnit.UN,
    @Contextual val quantity: BigDecimal = BigDecimal.ZERO
) {
    val stockDescription get(): String = "$quantity $measurementUnit"
}
