package br.edu.utfpr.apppizzaria.ui.user.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.edu.utfpr.apppizzaria.data.network.ApiService
import br.edu.utfpr.apppizzaria.data.network.decodeErrorBody
import br.edu.utfpr.apppizzaria.data.network.errors.ErrorData
import br.edu.utfpr.apppizzaria.data.pizzeria.request.PizzeriaLoginRequest
import br.edu.utfpr.apppizzaria.data.user.local.UserLogged
import br.edu.utfpr.apppizzaria.data.user.local.UserLoggedDatasource
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormField
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormFieldUtils
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormFieldUtils.Companion.validateFieldRequired
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class FormState(
    val login: FormField = FormField(),
    val password: FormField = FormField()
) {
    val isValid
        get(): Boolean = FormFieldUtils.isValid(
            listOf(
                login,
                password
            )
        )
}

data class LoginRegisterUiState(
    val formState: FormState = FormState(),
    val isProcessing: Boolean = false,
    val hasUnexpectedError: Boolean = false,
    val hasHttpError: Boolean = false,
    val errorBody: ErrorData = ErrorData(),
    val loginSuccess: Boolean = false
)


class LoginViewModel(private val userLoggedDatasource: UserLoggedDatasource) : ViewModel() {

    private val tag: String = "LoginViewModel"
    var uiState: LoginRegisterUiState by mutableStateOf(LoginRegisterUiState())

    fun login() {
        if (!isValidForm()) {
            return
        }

        uiState = uiState.copy(
            isProcessing = true,
            hasUnexpectedError = false,
            hasHttpError = false,
            errorBody = ErrorData()
        )

        viewModelScope.launch {
            uiState = try {
                userLoggedDatasource.saveUserLogged(loginPizzeria())

                uiState.copy(
                    isProcessing = false,
                    loginSuccess = true
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao efetuar o login", ex)

                if (ex is HttpException) {
                    uiState.copy(
                        isProcessing = false,
                        hasHttpError = true,
                        errorBody = decodeErrorBody(ex.response()?.errorBody()?.string())!!
                    )
                } else {
                    uiState.copy(
                        isProcessing = false,
                        hasUnexpectedError = true
                    )
                }
            }
        }
    }

    private suspend fun loginPizzeria(): UserLogged {
        val userLogged = ApiService.pizzerias.login(
            PizzeriaLoginRequest(
                login = uiState.formState.login.value,
                password = uiState.formState.password.value
            )
        )

        return UserLogged(
            id = userLogged.id,
            name = userLogged.name
        )
    }

    fun onLoginChanged(login: String) {
        if (uiState.formState.login.value != login) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    login = uiState.formState.login.copy(
                        value = login.lowercase(),
                        errorMessageCode = validateFieldRequired(login)
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
                        errorMessageCode = validateFieldRequired(password)
                    )
                )
            )
        }
    }

    fun onClearLogin() {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                login = uiState.formState.login.copy(
                    value = ""
                )
            )
        )
    }

    private fun isValidForm(): Boolean {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                login = uiState.formState.login.copy(
                    errorMessageCode = validateFieldRequired(uiState.formState.login.value)
                ),
                password = uiState.formState.password.copy(
                    errorMessageCode = validateFieldRequired(uiState.formState.password.value)
                )
            )
        )

        return uiState.formState.isValid
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]

                LoginViewModel(
                    userLoggedDatasource = UserLoggedDatasource(context = application!!)
                )
            }
        }
    }
}