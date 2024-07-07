package br.edu.utfpr.apppizzaria.ui.pizza.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.apppizzaria.data.network.ApiService
import br.edu.utfpr.apppizzaria.data.pizza.response.PizzaDefaultResponse
import kotlinx.coroutines.launch

data class PizzaListUiState(
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val pizzas: List<PizzaDefaultResponse> = listOf()
) {
    val isSuccess get(): Boolean = !loading && !hasError
}

class PizzaListViewModel : ViewModel() {

    private val tag: String = "PizzaListViewModel"
    var uiState: PizzaListUiState by mutableStateOf(PizzaListUiState())

    init {
        loadPizzas()
    }

    fun loadPizzas() {
        uiState = uiState.copy(
            loading = true,
            hasError = false
        )

        viewModelScope.launch {
            uiState = try {
                uiState.copy(
                    pizzas = ApiService.pizzas.findAll(),
                    loading = false
                )
            } catch (ex: Exception) {
                Log.e(tag, "Erro ao listar as pizzas", ex)
                uiState.copy(
                    hasError = true,
                    loading = false
                )
            }
        }
    }
}