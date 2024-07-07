package br.edu.utfpr.apppizzaria.data.pizza.request

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class PizzaUpdateRequest(
    val name: String = "",
    @Contextual val price: BigDecimal = BigDecimal.ZERO
)
