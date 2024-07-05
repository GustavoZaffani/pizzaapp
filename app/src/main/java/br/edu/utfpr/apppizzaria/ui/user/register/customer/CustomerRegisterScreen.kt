package br.edu.utfpr.apppizzaria.ui.user.register.customer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalPizza
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.data.pizzeria.enumerations.State
import br.edu.utfpr.apppizzaria.ui.shared.components.SectionHeader
import br.edu.utfpr.apppizzaria.ui.shared.components.form.DropdownField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.EmailField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.PasswordField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.PhoneField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.TextField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.ZipCodeField
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme
import br.edu.utfpr.apppizzaria.ui.user.register.WelcomeCard

@Composable
fun CustomerRegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: CustomerRegisterViewModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onRegisterSaved: () -> Unit
) {
    LaunchedEffect(viewModel.uiState.customerSaved) {
        if (viewModel.uiState.customerSaved) {
//            snackbarHostState.showSnackbar(
//                "Pizzaria registrada com sucesso. Por favor, efetue o login."
//            )
            // TODO: ver melhor pratica de mensagem de sucesso
            onRegisterSaved()
        }
    }

    LaunchedEffect(snackbarHostState, viewModel.uiState.hasErrorSaving) {
        if (viewModel.uiState.hasErrorSaving) {
            snackbarHostState.showSnackbar(
                "Não foi possível registrar a pizzaria. Aguarde um momento e tente novamente."
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())) {

            WelcomeCard(
                icon = Icons.Outlined.Person
            )
            FormContent(
                formState = viewModel.uiState.formState,
                onNameChanged = viewModel::onNameChanged,
                onPhoneChanged = viewModel::onPhoneChanged,
                onEmailChanged = viewModel::onEmailChanged,
                onLoginChanged = viewModel::onLoginChanged,
                onPasswordChanged = viewModel::onPasswordChanged,
                onClearValueName = viewModel::onClearValueName,
                onClearValuePhone = viewModel::onClearValuePhone,
                onClearValueEmail = viewModel::onClearValueEmail,
                onClearValueLogin = {}
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = viewModel::save
            ) {

                Text(text = "Registrar")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun CustomerRegisterScreenPreview() {
    AppPizzariaTheme {
        CustomerRegisterScreen(
            onRegisterSaved = {}
        )
    }
}

@Composable
private fun FormContent(
    modifier: Modifier = Modifier,
    formState: FormState,
    onNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClearValueName: () -> Unit,
    onClearValuePhone: () -> Unit,
    onClearValueEmail: () -> Unit,
    onClearValueLogin: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        SectionHeader(
            text = "Dados gerais"
        )
        TextField(
            label = "Nome",
            value = formState.name.value,
            onValueChange = onNameChanged,
            errorMessageCode = formState.name.errorMessageCode,
            onClearValue = onClearValueName
        )
        PhoneField(
            label = "Telefone",
            value = formState.phone.value,
            onValueChange = onPhoneChanged,
            errorMessageCode = formState.phone.errorMessageCode,
            onClearValue = onClearValuePhone
        )
        EmailField(
            label = "Email",
            value = formState.email.value,
            onValueChange = onEmailChanged,
            errorMessageCode = formState.email.errorMessageCode,
            onClearValue = onClearValueEmail
        )

        SectionHeader(
            text = "Dados de autenticação"
        )
        TextField(
            label = "Login",
            value = formState.login.value,
            onValueChange = onLoginChanged,
            errorMessageCode = formState.login.errorMessageCode,
            onClearValue = onClearValueLogin
        )
        PasswordField(
            label = "Senha",
            value = formState.password.value,
            onValueChange = onPasswordChanged,
            errorMessageCode = formState.password.errorMessageCode
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FormContentPreview() {
    AppPizzariaTheme {
        FormContent(
            formState = FormState(),
            onNameChanged = {},
            onPhoneChanged = {},
            onEmailChanged = {},
            onLoginChanged = {},
            onPasswordChanged = {},
            onClearValueName = {},
            onClearValuePhone = {},
            onClearValueEmail = {},
            onClearValueLogin = {}
        )
    }
}
