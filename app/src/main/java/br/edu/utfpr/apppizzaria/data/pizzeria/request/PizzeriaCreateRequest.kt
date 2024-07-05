package br.edu.utfpr.apppizzaria.data.pizzeria.request

import kotlinx.serialization.Serializable

@Serializable
data class PizzeriaCreateRequest(
    val name: String = "",
    val address: AddressRequest = AddressRequest(),
    val phone: String = "",
    val email: String = "",
    val login: String = "",
    val password: String = ""
)
