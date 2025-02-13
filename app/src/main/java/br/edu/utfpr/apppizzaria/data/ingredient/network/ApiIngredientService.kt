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
import retrofit2.http.Query
import java.util.UUID

interface ApiIngredientService {

    @GET("v1/ingredients")
    suspend fun findAll(@Query("ingredientName") ingredientName: String = ""): List<IngredientDefaultResponse>

    @GET("v1/ingredients/{id}")
    suspend fun findById(@Path("id") id: UUID): IngredientDefaultResponse

    @POST("v1/ingredients")
    suspend fun insert(@Body ingredient: IngredientCreateRequest): IngredientDefaultResponse

    @PATCH("v1/ingredients/{id}")
    suspend fun update(@Body ingredient: IngredientUpdateRequest,
                       @Path("id") id: UUID): IngredientDefaultResponse
}