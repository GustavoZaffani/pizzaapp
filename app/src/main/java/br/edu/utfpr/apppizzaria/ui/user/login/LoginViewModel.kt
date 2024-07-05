package br.edu.utfpr.apppizzaria.ui.user.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.edu.utfpr.apppizzaria.data.user.local.UserLogged
import br.edu.utfpr.apppizzaria.data.user.local.UserLoggedDatasource
import br.edu.utfpr.apppizzaria.data.user.local.UserType
import br.edu.utfpr.apppizzaria.ui.Arguments
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormField
import br.edu.utfpr.apppizzaria.ui.shared.utils.FormFieldUtils
import kotlinx.coroutines.launch
import java.util.UUID

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
    val hasErrorLogin: Boolean = false,
    val loginSuccess: Boolean = false
)


class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    private val userLoggedDatasource: UserLoggedDatasource
) : ViewModel() {

    private val tag: String = "LoginViewModel"
    var uiState: LoginRegisterUiState by mutableStateOf(LoginRegisterUiState())
    val userType: UserType? =
        savedStateHandle.get<String>(Arguments.USER_TYPE)?.let { UserType.valueOf(it) }

    fun login() {
        uiState = uiState.copy(
            isProcessing = true,
            hasErrorLogin = false
        )

        viewModelScope.launch {
            uiState = try {
                val userLogged =
                    if (userType == UserType.PIZZERIA) loginPizzeria() else loginCustomer()

                userLoggedDatasource.saveUserLogged(userLogged)

                uiState.copy(
                    isProcessing = false,
                    loginSuccess = true
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao efetuar o login", ex)
                uiState.copy(
                    isProcessing = false,
                    hasErrorLogin = true
                )
            }
        }
    }

    // TODO: implementar chamada
    fun loginPizzeria(): UserLogged {
        return UserLogged(
            id = UUID.fromString("1b0c4a25-92c8-4d69-858f-c3d0a7523206"),
            name = "Taberna",
            userType = UserType.PIZZERIA
        )
    }

    // TODO: implementar chamada
    fun loginCustomer(): UserLogged {
        return UserLogged(
            id = UUID.fromString(""),
            name = "",
            userType = UserType.CUSTOMER
        )
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val savedStateHandle = this.createSavedStateHandle()

                LoginViewModel(
                    savedStateHandle = savedStateHandle,
                    userLoggedDatasource = UserLoggedDatasource(context = application!!)
                )
            }
        }
    }
}