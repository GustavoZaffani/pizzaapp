package br.edu.utfpr.apppizzaria.ui.user.register.pizzeria

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalPizza
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.R
import br.edu.utfpr.apppizzaria.data.pizzeria.enumerations.State
import br.edu.utfpr.apppizzaria.ui.shared.components.ErrorDetails
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
fun PizzeriaRegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: PizzeriaRegisterViewModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onRegisterSaved: () -> Unit
) {
    val context = LocalContext.current
    var showHttpErrorModal by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.uiState.pizzeriaSaved) {
        if (viewModel.uiState.pizzeriaSaved) {
            Toast.makeText(
                context,
                context.getString(R.string.pizzeria_register_pizzeria_save_success),
                Toast.LENGTH_LONG
            ).show()
            onRegisterSaved()
        }
    }

    LaunchedEffect(snackbarHostState, viewModel.uiState.hasUnexpectedError) {
        if (viewModel.uiState.hasUnexpectedError) {
            snackbarHostState.showSnackbar(
                context.getString(R.string.pizzeria_register_unexpected_error)
            )
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

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            WelcomeCard(
                icon = Icons.Outlined.LocalPizza,
                user = viewModel.uiState.formState.name.value
            )
            FormContent(
                formState = viewModel.uiState.formState,
                allFormDisable = viewModel.uiState.isSaving,
                onNameChanged = viewModel::onNameChanged,
                onPhoneChanged = viewModel::onPhoneChanged,
                onEmailChanged = viewModel::onEmailChanged,
                onZipCodeChanged = viewModel::onZipCodeChanged,
                onStreetChanged = viewModel::onStreetChanged,
                onNeighborhoodChanged = viewModel::onNeighborhoodChanged,
                onCityChanged = viewModel::onCityChanged,
                onNumberChanged = viewModel::onNumberChanged,
                onStateChanged = viewModel::onStateChanged,
                onComplementChanged = viewModel::onComplementChanged,
                onLoginChanged = viewModel::onLoginChanged,
                onPasswordChanged = viewModel::onPasswordChanged,
                onClearValueName = viewModel::onClearValueName,
                onClearValuePhone = viewModel::onClearValuePhone,
                onClearValueEmail = viewModel::onClearValueEmail,
                onClearValueZipCode = viewModel::onClearValueZipCode,
                onClearValueStreet = viewModel::onClearValueStreet,
                onClearValueNeighborhood = viewModel::onClearValueNeighborhood,
                onClearValueCity = viewModel::onClearValueCity,
                onClearValueNumber = viewModel::onClearValueNumber,
                onClearValueComplement = viewModel::onClearValueComplement,
                onClearValueLogin = viewModel::onClearValueLogin
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = viewModel::save
            ) {
                if (viewModel.uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text(text = stringResource(R.string.generic_to_register))
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PizzeriaRegisterScreenPreview() {
    AppPizzariaTheme {
        PizzeriaRegisterScreen(
            onRegisterSaved = {}
        )
    }
}

@Composable
private fun FormContent(
    modifier: Modifier = Modifier,
    formState: FormState,
    allFormDisable: Boolean = false,
    onNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onZipCodeChanged: (String) -> Unit,
    onStreetChanged: (String) -> Unit,
    onNeighborhoodChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onNumberChanged: (String) -> Unit,
    onStateChanged: (String) -> Unit,
    onComplementChanged: (String) -> Unit,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClearValueName: () -> Unit,
    onClearValuePhone: () -> Unit,
    onClearValueEmail: () -> Unit,
    onClearValueZipCode: () -> Unit,
    onClearValueStreet: () -> Unit,
    onClearValueNeighborhood: () -> Unit,
    onClearValueCity: () -> Unit,
    onClearValueNumber: () -> Unit,
    onClearValueComplement: () -> Unit,
    onClearValueLogin: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        SectionHeader(
            text = stringResource(R.string.pizzeria_register_section_general_data)
        )
        TextField(
            label = stringResource(R.string.pizzeria_register_field_name),
            value = formState.name.value,
            onValueChange = onNameChanged,
            errorMessageCode = formState.name.errorMessageCode,
            onClearValue = onClearValueName,
            enabled = !allFormDisable
        )
        PhoneField(
            label = stringResource(R.string.pizzeria_register_field_phone),
            value = formState.phone.value,
            onValueChange = onPhoneChanged,
            errorMessageCode = formState.phone.errorMessageCode,
            onClearValue = onClearValuePhone,
            enabled = !allFormDisable
        )
        EmailField(
            label = stringResource(R.string.pizzeria_register_field_email),
            value = formState.email.value,
            onValueChange = onEmailChanged,
            errorMessageCode = formState.email.errorMessageCode,
            onClearValue = onClearValueEmail,
            enabled = !allFormDisable
        )

        SectionHeader(
            text = stringResource(R.string.pizzeria_register_section_address)
        )
        ZipCodeField(
            label = stringResource(R.string.pizzeria_register_field_zip_code),
            value = formState.zipCode.value,
            onValueChange = onZipCodeChanged,
            errorMessageCode = formState.zipCode.errorMessageCode,
            onClearValue = onClearValueZipCode,
            enabled = !allFormDisable
        )
        TextField(
            label = stringResource(R.string.pizzeria_register_field_street),
            value = formState.street.value,
            onValueChange = onStreetChanged,
            errorMessageCode = formState.street.errorMessageCode,
            keyboardCapitalization = KeyboardCapitalization.Words,
            onClearValue = onClearValueStreet,
            enabled = !allFormDisable
        )
        TextField(
            label = stringResource(R.string.pizzeria_register_field_neighborhood),
            value = formState.neighborhood.value,
            onValueChange = onNeighborhoodChanged,
            errorMessageCode = formState.neighborhood.errorMessageCode,
            keyboardCapitalization = KeyboardCapitalization.Words,
            onClearValue = onClearValueNeighborhood,
            enabled = !allFormDisable
        )
        TextField(
            label = stringResource(R.string.pizzeria_register_field_city),
            value = formState.city.value,
            onValueChange = onCityChanged,
            errorMessageCode = formState.city.errorMessageCode,
            keyboardCapitalization = KeyboardCapitalization.Words,
            onClearValue = onClearValueCity,
            enabled = !allFormDisable
        )
        TextField(
            label = stringResource(R.string.pizzeria_register_field_number),
            value = formState.number.value,
            onValueChange = onNumberChanged,
            errorMessageCode = formState.number.errorMessageCode,
            onClearValue = onClearValueNumber,
            enabled = !allFormDisable
        )
        DropdownField(
            selectedValue = formState.state.value,
            label = stringResource(R.string.pizzeria_register_field_state),
            onValueChangedEvent = onStateChanged,
            options = State.getDescriptionList(),
            enabled = !allFormDisable
        )
        TextField(
            label = stringResource(R.string.pizzeria_register_field_complement),
            value = formState.complement.value,
            onValueChange = onComplementChanged,
            errorMessageCode = formState.complement.errorMessageCode,
            onClearValue = onClearValueComplement,
            enabled = !allFormDisable
        )

        SectionHeader(
            text = stringResource(R.string.pizzeria_register_section_auth)
        )
        TextField(
            label = stringResource(R.string.pizzeria_register_field_login),
            value = formState.login.value,
            onValueChange = onLoginChanged,
            errorMessageCode = formState.login.errorMessageCode,
            onClearValue = onClearValueLogin,
            enabled = !allFormDisable
        )
        PasswordField(
            label = stringResource(R.string.pizzeria_register_field_password),
            value = formState.password.value,
            onValueChange = onPasswordChanged,
            errorMessageCode = formState.password.errorMessageCode,
            keyboardImeAction = ImeAction.Done,
            enabled = !allFormDisable
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
            onZipCodeChanged = {},
            onStreetChanged = {},
            onNeighborhoodChanged = {},
            onCityChanged = {},
            onNumberChanged = {},
            onStateChanged = {},
            onComplementChanged = {},
            onLoginChanged = {},
            onPasswordChanged = {},
            onClearValueName = {},
            onClearValuePhone = {},
            onClearValueEmail = {},
            onClearValueZipCode = {},
            onClearValueStreet = {},
            onClearValueNeighborhood = {},
            onClearValueCity = {},
            onClearValueNumber = {},
            onClearValueComplement = {},
            onClearValueLogin = {}
        )
    }
}
