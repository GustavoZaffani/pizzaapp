package br.edu.utfpr.apppizzaria.ui.user.register.customer

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
    val login: FormField = FormField(),
    val password: FormField = FormField(),
) {
    val isValid
        get(): Boolean = FormFieldUtils.isValid(
            listOf(
                name,
                phone,
                email,
                login,
                password
            )
        )
}

data class CustomerRegisterUiState(
    val formState: FormState = FormState(),
    val isSaving: Boolean = false,
    val hasErrorSaving: Boolean = false,
    val customerSaved: Boolean = false
)

class CustomerRegisterViewModel : ViewModel() {

    private val tag: String = "CustomerRegisterViewModel"
    var uiState: CustomerRegisterUiState by mutableStateOf(CustomerRegisterUiState())

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
                    customerSaved = true
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao registrar o cliente", ex)
                uiState.copy(
                    isSaving = false,
                    hasErrorSaving = true
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
}