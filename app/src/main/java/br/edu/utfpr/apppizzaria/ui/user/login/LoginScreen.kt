package br.edu.utfpr.apppizzaria.ui.user.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SupervisedUserCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.data.user.local.UserType
import br.edu.utfpr.apppizzaria.ui.ingredient.form.IngredientFormViewModel
import br.edu.utfpr.apppizzaria.ui.shared.components.ClickableTextDefault
import br.edu.utfpr.apppizzaria.ui.shared.components.form.TextField
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
    onClickNewRegister: (UserType) -> Unit,
    onLoginSuccess: () -> Unit
) {
    LaunchedEffect(viewModel.uiState.loginSuccess) {
        if (viewModel.uiState.loginSuccess) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
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
            label = "Usuário",
            value = viewModel.uiState.formState.login.value,
            onValueChange = viewModel::onLoginChanged,
            onClearValue = {})

        TextField(
            label = "Senha",
            value = viewModel.uiState.formState.password.value,
            onValueChange = viewModel::onPasswordChanged,
            onClearValue = {})

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            onClick = viewModel::login) {

            Text(text = "Entrar")
        }

        ClickableTextDefault(
            modifier = Modifier.padding(top = 8.dp),
            preText = "Ainda não tem uma conta?",
            clickText = "Crie uma agora",
            onClick = { onClickNewRegister(viewModel.userType!!) }
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
