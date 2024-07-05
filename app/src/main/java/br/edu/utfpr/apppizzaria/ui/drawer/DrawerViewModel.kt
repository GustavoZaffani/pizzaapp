package br.edu.utfpr.apppizzaria.ui.drawer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.edu.utfpr.apppizzaria.data.user.local.UserLoggedDatasource
import kotlinx.coroutines.launch

data class DrawerUiState(
    val isProcessingLogout: Boolean = false,
    val hasErrorLogout: Boolean = false,
    val logoutSuccess: Boolean = false,
    val userLogged: String = ""
)

class DrawerViewModel(
    private val userLoggedDatasource: UserLoggedDatasource
) : ViewModel() {

    private val tag: String = "DrawerViewModel"
    var uiState: DrawerUiState by mutableStateOf(DrawerUiState())

    init {
        loadUserLogged()
    }

    private fun loadUserLogged() {
        viewModelScope.launch {
            uiState = uiState.copy(
                userLogged = userLoggedDatasource.getUserLogged()!!.name
            )
        }
    }

    fun logout() {
        uiState = uiState.copy(
            isProcessingLogout = true,
            hasErrorLogout = false
        )

        viewModelScope.launch {
            uiState = try {
                userLoggedDatasource.clearUserLogged()

                uiState.copy(
                    isProcessingLogout = false,
                    logoutSuccess = true
                )
            } catch (ex: Exception) {
                Log.d(tag, "Erro ao efetuar o logout", ex)
                uiState.copy(
                    isProcessingLogout = false,
                    hasErrorLogout = true
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]

                DrawerViewModel(
                    userLoggedDatasource = UserLoggedDatasource(context = application!!)
                )
            }
        }
    }
}