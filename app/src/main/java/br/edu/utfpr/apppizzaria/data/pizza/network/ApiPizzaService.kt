package br.edu.utfpr.apppizzaria.data.pizza.network

import br.edu.utfpr.apppizzaria.data.pizza.request.PizzaCreateRequest
import br.edu.utfpr.apppizzaria.data.pizza.request.PizzaUpdateRequest
import br.edu.utfpr.apppizzaria.data.pizza.response.PizzaDefaultResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface ApiPizzaService {

    @GET("v1/pizzas")
    suspend fun findAll(): List<PizzaDefaultResponse>

    @GET("v1/pizzas/{id}")
    suspend fun findById(@Path("id") id: UUID): PizzaDefaultResponse

    @POST("v1/pizzas")
    suspend fun create(@Body pizza: PizzaCreateRequest): PizzaDefaultResponse

    @PATCH("v1/pizzas/{id}")
    suspend fun update(
        @Body pizza: PizzaUpdateRequest,
        @Path("id") id: UUID
    ): PizzaDefaultResponse
}