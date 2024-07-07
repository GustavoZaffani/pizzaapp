package br.edu.utfpr.apppizzaria.data.pizza.response

import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

@Serializable
data class PizzaDefaultResponse(
    @Contextual val id: UUID = Utils.GERERIC_UUID,
    val name: String = "",
    @Contextual val price: BigDecimal = BigDecimal.ZERO,
    val ingredients: List<PizzaIngredientDefaultResponse> = listOf()
) {
    val ingredientList get(): String = "${ingredients.map { it.ingredientName }.joinToString()}"
}
