package br.edu.utfpr.apppizzaria.ui.sale.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.apppizzaria.data.network.ApiService
import br.edu.utfpr.apppizzaria.data.sale.response.SaleDefaultResponse
import kotlinx.coroutines.launch

data class SaleListUiState(
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val sales: List<SaleDefaultResponse> = listOf()
) {
    val isSuccess get(): Boolean = !loading && !hasError
}

class SaleListViewModel : ViewModel() {
    private val tag: String = "SaleListViewModel"
    var uiState: SaleListUiState by mutableStateOf(SaleListUiState())

    init {
        loadSales()
    }

    fun loadSales() {
        uiState = uiState.copy(
            loading = true,
            hasError = false
        )

        viewModelScope.launch {
            uiState = try {
                uiState.copy(
                    sales = ApiService.sales.findAll(),
                    loading = false
                )
            } catch (ex: Exception) {
                Log.e(tag, "Erro ao listar as vendas", ex)
                uiState.copy(
                    hasError = true,
                    loading = false
                )
            }
        }
    }
}