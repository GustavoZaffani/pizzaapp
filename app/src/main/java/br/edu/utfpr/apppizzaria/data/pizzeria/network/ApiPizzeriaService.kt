package br.edu.utfpr.apppizzaria.data.pizzeria.network

import br.edu.utfpr.apppizzaria.data.pizzeria.request.PizzeriaCreateRequest
import br.edu.utfpr.apppizzaria.data.pizzeria.request.PizzeriaLoginRequest
import br.edu.utfpr.apppizzaria.data.pizzeria.response.PizzeriaLoginResponse
import br.edu.utfpr.apppizzaria.data.pizzeria.response.PizzeriaSaveResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiPizzeriaService {

    @POST("v1/pizzerias")
    suspend fun create(@Body pizzeria: PizzeriaCreateRequest): PizzeriaSaveResponse

    @POST("v1/pizzerias/login")
    suspend fun login(@Body pizzeria: PizzeriaLoginRequest): PizzeriaLoginResponse
}