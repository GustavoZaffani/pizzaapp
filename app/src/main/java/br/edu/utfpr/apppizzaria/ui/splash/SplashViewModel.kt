package br.edu.utfpr.apppizzaria.ui.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SplashUIState(
    val visible: Boolean = true
)

class SplashViewModel : ViewModel() {

    var uiState: SplashUIState by mutableStateOf(SplashUIState())

    init {
        viewModelScope.launch {
            delay(3000)
            Log.e("a", "terminou")
            uiState = uiState.copy(
                visible = false
            )
        }
    }
}