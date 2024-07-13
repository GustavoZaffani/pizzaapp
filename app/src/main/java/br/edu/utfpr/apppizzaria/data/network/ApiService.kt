package br.edu.utfpr.apppizzaria.data.network

import br.edu.utfpr.apppizzaria.data.ingredient.network.ApiIngredientService
import br.edu.utfpr.apppizzaria.data.network.errors.ErrorData
import br.edu.utfpr.apppizzaria.data.network.serializers.BigDecimalSerializer
import br.edu.utfpr.apppizzaria.data.network.serializers.DateSerializer
import br.edu.utfpr.apppizzaria.data.network.serializers.UUIDSerializer
import br.edu.utfpr.apppizzaria.data.pizza.network.ApiPizzaService
import br.edu.utfpr.apppizzaria.data.pizzeria.network.ApiPizzeriaService
import br.edu.utfpr.apppizzaria.data.sale.network.ApiSaleService
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

private val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    serializersModule = SerializersModule {
        contextual(BigDecimal::class, BigDecimalSerializer)
        contextual(UUID::class, UUIDSerializer)
        contextual(Date::class, DateSerializer)
    }
}

private val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor())
    .build()

private const val API_PIZZA_BASE_URL = "http://192.168.68.121:8080/"
private val apiPizzaClient = Retrofit.Builder()
    .client(okHttpClient)
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

    val pizzas: ApiPizzaService by lazy {
        apiPizzaClient.create(ApiPizzaService::class.java)
    }

    val sales: ApiSaleService by lazy {
        apiPizzaClient.create(ApiSaleService::class.java)
    }
}

fun decodeErrorBody(errorBody: String?): ErrorData? {
    return if (errorBody != null) {
        try {
            json.decodeFromString<ErrorData>(errorBody)
        } catch (e: Exception) {
            null
        }
    } else {
        null
    }
}