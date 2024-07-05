package br.edu.utfpr.apppizzaria.data.pizzeria.request

import kotlinx.serialization.Serializable

@Serializable
data class AddressRequest(
    val zipCode: String = "",
    val street: String = "",
    val neighborhood: String = "",
    val city: String = "",
    val number: String = "",
    val state: String = "",
    val complement: String = "",
)
