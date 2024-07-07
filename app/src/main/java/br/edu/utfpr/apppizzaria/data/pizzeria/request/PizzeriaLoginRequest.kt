package br.edu.utfpr.apppizzaria.data.pizzeria.request

import kotlinx.serialization.Serializable

@Serializable
data class PizzeriaLoginRequest(
    val login: String = "",
    val password: String = ""
)
