package br.edu.utfpr.apppizzaria.data.ingredient.network

import br.edu.utfpr.apppizzaria.data.ingredient.request.IngredientCreateRequest
import br.edu.utfpr.apppizzaria.data.ingredient.request.IngredientUpdateRequest
import br.edu.utfpr.apppizzaria.data.ingredient.response.IngredientDefaultResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface ApiIngredientService {

    @GET("v1/ingredients")
    suspend fun findAll(): List<IngredientDefaultResponse>

    @GET("v1/ingredients/{id}")
    suspend fun findById(@Path("id") id: UUID): IngredientDefaultResponse

    @DELETE("v1/ingredients/{id}")
    suspend fun delete(@Path("id") id: UUID)

    @POST("v1/ingredients")
    suspend fun insert(@Body ingredient: IngredientCreateRequest): List<IngredientDefaultResponse>

    @PATCH("v1/ingredients/{id}")
    suspend fun insert(@Body ingredient: IngredientUpdateRequest): List<IngredientDefaultResponse>
}