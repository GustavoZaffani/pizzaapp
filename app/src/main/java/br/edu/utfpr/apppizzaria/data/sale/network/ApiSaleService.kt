package br.edu.utfpr.apppizzaria.data.sale.network

import br.edu.utfpr.apppizzaria.data.sale.response.SaleDefaultResponse
import retrofit2.http.GET

interface ApiSaleService {

    @GET("v1/sales")
    suspend fun findAll(): List<SaleDefaultResponse>
}