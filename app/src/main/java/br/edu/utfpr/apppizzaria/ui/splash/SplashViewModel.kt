package br.edu.utfpr.apppizzaria.ui.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.edu.utfpr.apppizzaria.data.user.local.UserLoggedDatasource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SplashUIState(
    val visible: Boolean = true,
    val userLogged: Boolean = false
)

class SplashViewModel(
    private val userLoggedDatasource: UserLoggedDatasource
) : ViewModel() {

    var uiState: SplashUIState by mutableStateOf(SplashUIState())

    init {
        checkUserLogged()
    }

    private fun checkUserLogged() {
        viewModelScope.launch {
            delay(1) // TODO: aumentar timer

            val userLogged = userLoggedDatasource.getUserLogged()

            uiState = uiState.copy(
                visible = false,
                userLogged = userLogged != null
            )
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                SplashViewModel(
                    userLoggedDatasource = UserLoggedDatasource(context = application!!)
                )
            }
        }
    }
}