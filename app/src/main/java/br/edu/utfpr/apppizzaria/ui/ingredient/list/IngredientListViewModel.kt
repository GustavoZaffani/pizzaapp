package br.edu.utfpr.apppizzaria.ui.ingredient.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.apppizzaria.data.ingredient.response.IngredientDefaultResponse
import br.edu.utfpr.apppizzaria.data.network.ApiService
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormField
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormFieldUtils
import kotlinx.coroutines.launch

data class FormState(
    val name: FormField = FormField()
)

data class IngredientListUiState(
    val formState: FormState = FormState(),
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val ingredients: List<IngredientDefaultResponse> = listOf()
) {
    val isSuccess get(): Boolean = !loading && !hasError
}

class IngredientsListViewModel : ViewModel() {

    private val tag: String = "IngredientsListViewModel"
    var uiState: IngredientListUiState by mutableStateOf(IngredientListUiState())

    init {
        loadIngredients()
    }

    fun loadIngredients() {
        uiState = uiState.copy(
            loading = true,
            hasError = false
        )

        viewModelScope.launch {
            uiState = try {
                val ingredients = ApiService.ingredients.findAll(
                    uiState.formState.name.value
                )

                uiState.copy(
                    ingredients = ingredients,
                    loading = false
                )
            } catch (ex: Exception) {
                Log.e(tag, "Erro ao listar os ingredientes", ex)
                uiState.copy(
                    hasError = true,
                    loading = false
                )
            }
        }
    }

    fun onFilterChanged(name: String) {
        if (uiState.formState.name.value != name) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    name = uiState.formState.name.copy(
                        value = name
                    )
                )
            )
        }
    }

    fun onClearFilter() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                name = uiState.formState.name.copy(
                    value = ""
                )
            )
        )
    }
}