package br.edu.utfpr.apppizzaria.data.network.errors;

import kotlinx.serialization.Serializable

@Serializable
data class ErrorField(
    val field: String = "",
    val error: String = ""
)