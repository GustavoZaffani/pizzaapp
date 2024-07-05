package br.edu.utfpr.apppizzaria.ui.user.register.pizzeria

import android.util.JsonWriter
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.apppizzaria.data.network.ApiService
import br.edu.utfpr.apppizzaria.data.pizzeria.enumerations.State
import br.edu.utfpr.apppizzaria.data.pizzeria.request.AddressRequest
import br.edu.utfpr.apppizzaria.data.pizzeria.request.PizzeriaCreateRequest
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormField
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormFieldUtils
import kotlinx.coroutines.launch

data class FormState(
    val name: FormField = FormField(),
    val phone: FormField = FormField(),
    val email: FormField = FormField(),
    val zipCode: FormField = FormField(),
    val street: FormField = FormField(),
    val neighborhood: FormField = FormField(),
    val city: FormField = FormField(),
    val number: FormField = FormField(),
    val state: FormField = FormField(),
    val complement: FormField = FormField(),
    val login: FormField = FormField(),
    val password: FormField = FormField(),
) {
    val isValid
        get(): Boolean = FormFieldUtils.isValid(
            listOf(
                name,
                phone,
                email,
                zipCode,
                street,
                neighborhood,
                city,
                number,
                state,
                complement,
                login,
                password
            )
        )
}

data class PizzeriaRegisterUiState(
    val formState: FormState = FormState(),
    val isSaving: Boolean = false,
    val hasErrorSaving: Boolean = false,
    val pizzeriaSaved: Boolean = false
)

class PizzeriaRegisterViewModel : ViewModel() {

    private val tag: String = "PizzeriaRegisterViewModel"
    var uiState: PizzeriaRegisterUiState by mutableStateOf(PizzeriaRegisterUiState())

    fun save() {
        uiState = uiState.copy(
            isSaving = true,
            hasErrorSaving = false
        )

        viewModelScope.launch {
            uiState = try {
//                ApiService.pizzerias.create(buildObject())

                uiState.copy(
                    isSaving = false,
                    pizzeriaSaved = true
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao registrar a pizzaria", ex)
                uiState.copy(
                    isSaving = false,
                    hasErrorSaving = true
                )
            }
        }
    }
    private fun buildObject(): PizzeriaCreateRequest {
        return PizzeriaCreateRequest(
            name = uiState.formState.name.value,
            address = AddressRequest(
                zipCode = uiState.formState.zipCode.value,
                street = uiState.formState.street.value,
                neighborhood = uiState.formState.neighborhood.value,
                city = uiState.formState.city.value,
                number = uiState.formState.number.value,
                state = State.fromDescription(uiState.formState.state.value).toString(),
                complement = uiState.formState.complement.value,
            ),
            phone = uiState.formState.phone.value,
            email = uiState.formState.email.value,
            login = uiState.formState.login.value,
            password = uiState.formState.password.value,
        )
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

    fun onPhoneChanged(phone: String) {
        if (uiState.formState.phone.value != phone) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    phone = uiState.formState.phone.copy(
                        value = phone,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onEmailChanged(email: String) {
        if (uiState.formState.email.value != email) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    email = uiState.formState.email.copy(
                        value = email,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onZipCodeChanged(zipCode: String) {
        if (uiState.formState.zipCode.value != zipCode) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    zipCode = uiState.formState.zipCode.copy(
                        value = zipCode,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onStreetChanged(street: String) {
        if (uiState.formState.street.value != street) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    street = uiState.formState.street.copy(
                        value = street,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onNeighborhoodChanged(neighborhood: String) {
        if (uiState.formState.neighborhood.value != neighborhood) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    neighborhood = uiState.formState.neighborhood.copy(
                        value = neighborhood,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onCityChanged(city: String) {
        if (uiState.formState.city.value != city) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    city = uiState.formState.city.copy(
                        value = city,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onNumberChanged(number: String) {
        if (uiState.formState.number.value != number) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    number = uiState.formState.number.copy(
                        value = number,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onStateChanged(state: String) {
        if (uiState.formState.state.value != state) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    state = uiState.formState.state.copy(
                        value = state,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onComplementChanged(complement: String) {
        if (uiState.formState.complement.value != complement) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    complement = uiState.formState.complement.copy(
                        value = complement,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onLoginChanged(login: String) {
        if (uiState.formState.login.value != login) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    login = uiState.formState.login.copy(
                        value = login,
//                        errorMessageCode = validateNome(nome)
                    )
                )
            )
        }
    }

    fun onPasswordChanged(password: String) {
        if (uiState.formState.password.value != password) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    password = uiState.formState.password.copy(
                        value = password,
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

    fun onClearValuePhone() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                phone = uiState.formState.phone.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValueEmail() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                email = uiState.formState.email.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValueZipCode() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                zipCode = uiState.formState.zipCode.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValueStreet() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                street = uiState.formState.street.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValueNeighborhood() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                neighborhood = uiState.formState.neighborhood.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValueCity() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                city = uiState.formState.city.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValueNumber() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                number = uiState.formState.number.copy(
                    value = ""
                )
            )
        )
    }

    fun onClearValueComplement() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                complement = uiState.formState.complement.copy(
                    value = ""
                )
            )
        )
    }
}