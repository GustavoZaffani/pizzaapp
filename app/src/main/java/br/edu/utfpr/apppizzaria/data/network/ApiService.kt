package br.edu.utfpr.apppizzaria.data.network

import br.edu.utfpr.apppizzaria.data.ingredient.network.ApiIngredientService
import br.edu.utfpr.apppizzaria.data.pizzeria.enumerations.State
import br.edu.utfpr.apppizzaria.data.pizzeria.network.ApiPizzeriaService
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.math.BigDecimal
import java.util.UUID

private val json = Json {
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {
        contextual(BigDecimal::class, BigDecimalSerializer)
        contextual(UUID::class, UUIDSerializer)
    }
}
private val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())

private const val API_PIZZA_BASE_URL = "http://192.168.68.121:8080/"
private val apiPizzaClient = Retrofit.Builder()
    .addConverterFactory(jsonConverterFactory)
    .baseUrl(API_PIZZA_BASE_URL)
    .build()

object ApiService {
    val ingredients: ApiIngredientService by lazy {
        apiPizzaClient.create(ApiIngredientService::class.java)
    }

    val pizzerias: ApiPizzeriaService by lazy {
        apiPizzaClient.create(ApiPizzeriaService::class.java)
    }
}