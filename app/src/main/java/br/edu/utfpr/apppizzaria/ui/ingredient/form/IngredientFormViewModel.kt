package br.edu.utfpr.apppizzaria.ui.ingredient.form

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import br.edu.utfpr.apppizzaria.data.ingredient.request.IngredientCreateRequest
import br.edu.utfpr.apppizzaria.data.network.ApiService
import br.edu.utfpr.apppizzaria.ui.Arguments
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormFieldUtils
import br.edu.utfpr.apppizzaria.ui.shared.utils.Utils
import kotlinx.coroutines.launch
import java.util.UUID

data class FormField(
    val value: String = "",
    @StringRes
    val errorMessageCode: Int? = null
)

data class FormState(
    val name: FormField = FormField(),
    val description: FormField = FormField(),
    val price: FormField = FormField(),
    val measurementUnit: FormField = FormField(),
    val quantity: FormField = FormField(),
) {
    val isValid
        get(): Boolean = FormFieldUtils.isValid(
            listOf(
                name, description, price, measurementUnit, quantity
            )
        )
}

data class IngredientFormUiState(
    val ingredientId: UUID = Utils.GERERIC_UUID,
    val isLoading: Boolean = false,
    val hasErrorLoading: Boolean = false,
    val formState: FormState = FormState(),
    val isSaving: Boolean = false,
    val hasErrorSaving: Boolean = false,
    val ingredientSaved: Boolean = false
) {
    val isNewIngredient get(): Boolean = ingredientId == Utils.GERERIC_UUID
    val isSuccessLoading get(): Boolean = !isLoading && !hasErrorLoading
}

class IngredientFormViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tag: String = "IngredientFormViewModel"
    private val ingredientId: UUID? =
        savedStateHandle.get<String>(Arguments.INGREDIENT_ID)?.let { UUID.fromString(it) }
    var uiState: IngredientFormUiState by mutableStateOf(IngredientFormUiState())

    init {
        if (ingredientId != Utils.GERERIC_UUID) {
            loadIngredient()
        }
    }

    fun loadIngredient() {
        uiState = uiState.copy(
            isLoading = true,
            hasErrorLoading = false
        )

        viewModelScope.launch {
            uiState = try {
                val ingredient = ApiService.ingredients.findById(ingredientId!!)

                uiState.copy(
                    isLoading = false,
                    formState = FormState(
                        name = FormField(ingredient.name),
                        description = FormField(ingredient.description),
                        price = FormField(ingredient.price.toString()),
                        measurementUnit = FormField(ingredient.measurementUnit.description),
                        quantity = FormField(ingredient.quantity.toString())
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

    fun onDescriptionChanged(description: String) {
        if (uiState.formState.description.value != description) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    description = uiState.formState.description.copy(
                        value = description,
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

    fun onMeasurementUnitChanged(measurementUnit: String) {
        if (uiState.formState.measurementUnit.value != measurementUnit) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    measurementUnit = uiState.formState.measurementUnit.copy(
                        value = measurementUnit,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onQuantityChanged(quantity: String) {
        if (uiState.formState.quantity.value != quantity) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    quantity = uiState.formState.quantity.copy(
                        value = quantity,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onClearValueName() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                name = uiState.formState.name.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValueDescription() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                description = uiState.formState.description.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValuePrice() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                price = uiState.formState.price.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValueQuantity() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                quantity = uiState.formState.quantity.copy(
                    value = ""
                )
            )
        )
    }

    fun save() {
        if (ingredientId != Utils.GERERIC_UUID) {
            insert()
        }
    }

    fun insert() {
//        if (!isValidForm()) {
//            return
//        }

        uiState = uiState.copy(
            isSaving = true,
            hasErrorSaving = false
        )

        viewModelScope.launch {
            val ingredient = IngredientCreateRequest(
                name = uiState.formState.name.value,
                description = uiState.formState.description.value,
                price = uiState.formState.price.value.toBigDecimal(),
                measurementUnit = MeasurementUnit.fromDescription(uiState.formState.measurementUnit.value),
                quantity = uiState.formState.quantity.value.toBigDecimal(),
            )

            uiState = try {
                ApiService.ingredients.insert(ingredient)
                uiState.copy(
                    isSaving = false,
                    ingredientSaved = true
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao salvar o ingrediente", ex)
                uiState.copy(
                    isSaving = false,
                    hasErrorSaving = true
                )
            }
        }
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