package br.edu.utfpr.apppizzaria.data.pizza.request

import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

@Serializable
data class PizzaIngredientCreateRequest(
    @Contextual val ingredientId: UUID = Utils.GERERIC_UUID,
    @Contextual val quantity: BigDecimal = BigDecimal.ZERO
)
