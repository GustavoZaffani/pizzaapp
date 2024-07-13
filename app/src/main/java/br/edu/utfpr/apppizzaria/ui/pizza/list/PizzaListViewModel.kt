package br.edu.utfpr.apppizzaria.ui.pizza.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.apppizzaria.data.network.ApiService
import br.edu.utfpr.apppizzaria.data.pizza.response.PizzaDefaultResponse
import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.coroutines.launch
import java.util.UUID

data class PizzaListUiState(
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val pizzas: List<PizzaDefaultResponse> = listOf(),
    val isDeleting: Boolean = false,
    val hasErrorDeleting: Boolean = false,
    val pizzaDeleted: Boolean = false,
    val showConfirmationDialog: Boolean = false,
    val pizzaIdToDelete: UUID = Utils.GERERIC_UUID
) {
    val isSuccess get(): Boolean = !loading && !hasError
    val hasAnyLoading get(): Boolean = loading || isDeleting
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

    fun deletePizza() {
        uiState = uiState.copy(
            isDeleting = true,
            hasErrorDeleting = false,
            showConfirmationDialog = false
        )

        viewModelScope.launch {
            uiState = try {
                ApiService.pizzas.delete(uiState.pizzaIdToDelete)

                uiState.copy(
                    isDeleting = false,
                    pizzaDeleted = true,
                    pizzaIdToDelete = Utils.GERERIC_UUID
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao remover a pizza", ex)
                uiState.copy(
                    isDeleting = false,
                    hasErrorDeleting = true
                )
            }
        }
    }

    fun showConfirmationDialog(pizza: PizzaDefaultResponse) {
        uiState = uiState.copy(
            showConfirmationDialog = true,
            pizzaIdToDelete = pizza.id
        )
    }

    fun dismissConfirmationDialog() {
        uiState = uiState.copy(
            showConfirmationDialog = false,
            pizzaIdToDelete = Utils.GERERIC_UUID
        )
    }
}