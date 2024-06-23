package br.edu.utfpr.apppizzaria.data.ingredient.request

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class IngredientUpdateRequest(
    val name: String = "",
    val description: String = "",
    @Contextual val price: BigDecimal = BigDecimal.ZERO
)
