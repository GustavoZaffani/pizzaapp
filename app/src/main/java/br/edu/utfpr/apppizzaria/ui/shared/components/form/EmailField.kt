package br.edu.utfpr.apppizzaria.ui.shared.components.form

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onClearValue: () -> Unit,
    enabled: Boolean = true,
    @StringRes
    errorMessageCode: Int? = null,
    keyboardImeAction: ImeAction = ImeAction.Next
) {
    CustomTextField(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        errorMessageCode = errorMessageCode,
        keyboardImeAction = keyboardImeAction,
        keyboardCapitalization = KeyboardCapitalization.None,
        keyboardType = KeyboardType.Email,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.AlternateEmail,
                contentDescription = "Email"
            )
        },
        trailingIcon = {
            IconButton(onClick = onClearValue) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = "Limpar"
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PasswordFieldPreview() {
    AppPizzariaTheme {
        EmailField(
            label = "Email",
            value = "zaffani@gmail.com",
            onValueChange = {},
            onClearValue = {})
    }
}