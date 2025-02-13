package br.edu.utfpr.apppizzaria.ui.user.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SupervisedUserCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.R
import br.edu.utfpr.apppizzaria.ui.shared.components.ClickableTextDefault
import br.edu.utfpr.apppizzaria.ui.shared.components.ErrorDetails
import br.edu.utfpr.apppizzaria.ui.shared.components.Loading
import br.edu.utfpr.apppizzaria.ui.shared.components.form.PasswordField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.TextField
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
    onClickNewRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    var showHttpErrorModal by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.uiState.loginSuccess) {
        if (viewModel.uiState.loginSuccess) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(viewModel.uiState.hasUnexpectedError) {
        if (viewModel.uiState.hasUnexpectedError) {
            Toast.makeText(
                context,
                context.getString(R.string.login_error_login),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(viewModel.uiState.hasHttpError) {
        if (viewModel.uiState.hasHttpError) {
            showHttpErrorModal = true
        }
    }

    ErrorDetails(
        errorData = viewModel.uiState.errorBody,
        showModal = showHttpErrorModal,
        onDismissModal = { showHttpErrorModal = false }
    )

    if (viewModel.uiState.isProcessing) {
        Loading(text = stringResource(R.string.login_loading))
    } else {
        LoginContent(
            modifier = modifier.fillMaxSize(),
            formState = viewModel.uiState.formState,
            onLoginChanged = viewModel::onLoginChanged,
            onPasswordChanged = viewModel::onPasswordChanged,
            onClearLogin = viewModel::onClearLogin,
            onLogin = viewModel::login,
            onClickNewRegister = onClickNewRegister
        )
    }

}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    formState: FormState,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClearLogin: () -> Unit,
    onLogin: () -> Unit,
    onClickNewRegister: () -> Unit

) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Rounded.SupervisedUserCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(120.dp)
        )

        TextField(
            label = stringResource(R.string.login_field_user),
            value = formState.login.value,
            errorMessageCode = formState.login.errorMessageCode,
            onValueChange = onLoginChanged,
            onClearValue = onClearLogin,
            keyboardCapitalization = KeyboardCapitalization.None
        )

        PasswordField(
            label = stringResource(R.string.login_field_password),
            value = formState.password.value,
            errorMessageCode = formState.password.errorMessageCode,
            onValueChange = onPasswordChanged,
            keyboardImeAction = ImeAction.Done
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = onLogin
        ) {

            Text(text = stringResource(R.string.generic_to_enter))
        }

        ClickableTextDefault(
            modifier = Modifier.padding(top = 8.dp),
            preText = stringResource(R.string.login_new_register_title),
            clickText = stringResource(R.string.login_new_register_action),
            onClick = onClickNewRegister
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    AppPizzariaTheme {
        LoginScreen(
            onClickNewRegister = {},
            onLoginSuccess = {}
        )
    }
}
