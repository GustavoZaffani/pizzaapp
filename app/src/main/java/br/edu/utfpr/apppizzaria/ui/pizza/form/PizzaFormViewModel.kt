package br.edu.utfpr.apppizzaria.ui.pizza.form

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import br.edu.utfpr.apppizzaria.data.network.ApiService
import br.edu.utfpr.apppizzaria.data.pizza.request.PizzaCreateRequest
import br.edu.utfpr.apppizzaria.data.pizza.request.PizzaIngredientCreateRequest
import br.edu.utfpr.apppizzaria.data.pizza.request.PizzaUpdateRequest
import br.edu.utfpr.apppizzaria.ui.Arguments
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormField
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormFieldUtils
import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID

data class PizzaIngredientState(
    val name: String = "",
    val ingredientId: UUID = Utils.GERERIC_UUID,
    val measurementUnit: MeasurementUnit = MeasurementUnit.UN,
    var quantity: BigDecimal = BigDecimal.ZERO
)

data class FormState(
    val name: FormField = FormField(),
    val price: FormField = FormField(),
    val ingredients: List<PizzaIngredientState> = listOf()
) {
    val isValid
        get(): Boolean = FormFieldUtils.isValid(listOf(name, price)) && ingredients.isNotEmpty()

    val ingredientsAdded
        get(): List<String> = ingredients
            .filter { ingredient -> ingredient.quantity > BigDecimal.ZERO }
            .map { ingredient -> "${ingredient.name} - ${ingredient.quantity}/${ingredient.measurementUnit}" }
}

data class PizzaFormUiState(
    val pizzaId: UUID = Utils.GERERIC_UUID,
    val isLoading: Boolean = false,
    val hasErrorLoading: Boolean = false,
    val formState: FormState = FormState(),
    val isSaving: Boolean = false,
    val hasErrorSaving: Boolean = false,
    val pizzaSaved: Boolean = false
) {
    val isNewPizza get(): Boolean = pizzaId == Utils.GERERIC_UUID
    val isSuccessLoading get(): Boolean = !isLoading && !hasErrorLoading
}

class PizzaFormViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tag: String = "PizzaFormViewModel"
    private val pizzaId: UUID? =
        savedStateHandle.get<String>(Arguments.PIZZA_ID)?.let { UUID.fromString(it) }
    var uiState: PizzaFormUiState by mutableStateOf(PizzaFormUiState())

    init {
        if (pizzaId != null) {
            uiState = uiState.copy(
                pizzaId = pizzaId
            )
            loadPizza()
        } else {
            loadInfoToAddPizza()
        }
    }

    fun loadInfoToAddPizza() {
        uiState = uiState.copy(
            isLoading = true,
            hasErrorLoading = false
        )

        viewModelScope.launch {
            uiState = try {
                val ingredients = ApiService.ingredients.findAll()

                uiState.copy(
                    isLoading = false,
                    formState = FormState(
                        ingredients = ingredients.map {
                            PizzaIngredientState(
                                name = it.name,
                                ingredientId = it.id,
                                measurementUnit = it.measurementUnit,
                                quantity = BigDecimal.ZERO
                            )
                        }
                    )
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao carregar os dados do ingrediente", ex)
                uiState.copy(
                    isLoading = false,
                    hasErrorLoading = true
                )
            }
        }
    }

    fun loadPizza() {
        uiState = uiState.copy(
            isLoading = true,
            hasErrorLoading = false
        )

        viewModelScope.launch {
            uiState = try {
                val ingredient = ApiService.pizzas.findById(pizzaId!!)

                uiState.copy(
                    isLoading = false,
                    formState = FormState(
                        name = FormField(ingredient.name),
                        price = FormField(ingredient.price.toString()),
                        ingredients = ingredient.ingredients.map {
                            PizzaIngredientState(
                                ingredientId = it.ingredientId,
                                name = it.ingredientName,
                                measurementUnit = it.measurementUnit,
                                quantity = it.quantity
                            )
                        }
                    )
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao carregar os dados da pizza", ex)
                uiState.copy(
                    isLoading = false,
                    hasErrorLoading = true
                )
            }
        }
    }

    fun onNameChanged(name: String) {
        if (uiState.formState.name.value != name) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    name = uiState.formState.name.copy(
                        value = name,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onPriceChanged(price: String) {
        if (uiState.formState.price.value != price) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    price = uiState.formState.price.copy(
                        value = price,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onAddIngredient(ingredient: PizzaIngredientState) {
        val updatedIngredients = uiState.formState.ingredients.map {
            if (it.ingredientId == ingredient.ingredientId) {
                it.copy(quantity = it.quantity.add(BigDecimal.ONE))
            } else {
                it
            }
        }

        uiState = uiState.copy(
            formState = uiState.formState.copy(
                ingredients = updatedIngredients
            )
        )
    }

    fun onRemoveIngredient(ingredient: PizzaIngredientState) {
        val updatedIngredients = uiState.formState.ingredients.map {
            if (it.ingredientId == ingredient.ingredientId && it.quantity > BigDecimal.ZERO) {
                it.copy(quantity = it.quantity.minus(BigDecimal.ONE))
            } else {
                it
            }
        }

        uiState = uiState.copy(
            formState = uiState.formState.copy(
                ingredients = updatedIngredients
            )
        )
    }

    fun save() {
        uiState = uiState.copy(
            isSaving = true,
            hasErrorSaving = false
        )

        viewModelScope.launch {
            uiState = try {
                if (uiState.isNewPizza) {
                    ApiService.pizzas.create(buildObjectToInsert())
                } else {
                    ApiService.pizzas.update(buildObjectToUpdate(), uiState.pizzaId)
                }

                uiState.copy(
                    isSaving = false,
                    pizzaSaved = true
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao salvar a pizza", ex)
                uiState.copy(
                    isSaving = false,
                    hasErrorSaving = true
                )
            }
        }
    }

    private fun buildObjectToInsert(): PizzaCreateRequest {
        return PizzaCreateRequest(
            name = uiState.formState.name.value,
            price = uiState.formState.price.value.toBigDecimal(),
            ingredients = uiState.formState.ingredients.filter { it.quantity > BigDecimal.ZERO }
                .map {
                    PizzaIngredientCreateRequest(
                        quantity = it.quantity,
                        ingredientId = it.ingredientId
                    )
                }
        )
    }
//
    private fun buildObjectToUpdate(): PizzaUpdateRequest {
        return PizzaUpdateRequest(
            name = uiState.formState.name.value,
            price = uiState.formState.price.value.toBigDecimal()
        )
    }

//    fun isValidForm(): Boolean {
//        uiState = uiState.copy(
//            formState = uiState.formState.copy(
//                nome = uiState.formState.nome.copy(
//                    errorMessageCode = validateNome(uiState.formState.nome.value)
//                ),
//                cpf = uiState.formState.cpf.copy(
//                    errorMessageCode = validateCpf(uiState.formState.cpf.value)
//                ),
//                telefone = uiState.formState.telefone.copy(
//                    errorMessageCode = validateTelefone(uiState.formState.telefone.value)
//                )
//            )
//        )
//
//        return uiState.formState.isValid
//    }
}
