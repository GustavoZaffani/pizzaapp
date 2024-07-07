package br.edu.utfpr.apppizzaria.data.pizza.response

import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

@Serializable
data class PizzaIngredientDefaultResponse(
    @Contextual val id: UUID = Utils.GERERIC_UUID,
    @Contextual val ingredientId: UUID = Utils.GERERIC_UUID,
    val ingredientName: String = "",
    val measurementUnit: MeasurementUnit = MeasurementUnit.UN,
    @Contextual val quantity: BigDecimal = BigDecimal.ZERO
)
