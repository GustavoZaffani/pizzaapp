package br.edu.utfpr.apppizzaria.data.pizza.request

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class PizzaCreateRequest(
    val name: String = "",
    @Contextual val price: BigDecimal = BigDecimal.ZERO,
    val ingredients: List<PizzaIngredientCreateRequest> = listOf()
)
