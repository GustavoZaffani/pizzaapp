package br.edu.utfpr.apppizzaria.ui.shared.components.form

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.apppizzaria.ui.shared.visualtransformations.ZipCodeVisualTransformation
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@Composable
fun ZipCodeField(
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
        keyboardType = KeyboardType.Number,
        keyboardImeAction = keyboardImeAction,
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Place, contentDescription = "CEP")

        },
        trailingIcon = {
            IconButton(onClick = onClearValue) {
                Icon(imageVector = Icons.Outlined.Clear, contentDescription = "Limpar")
            }
        },
        visualTransformation = ZipCodeVisualTransformation()
    )
}

@Preview(showBackground = true)
@Composable
private fun ZipCodeFieldPreview() {
    AppPizzariaTheme {
        ZipCodeField(
            label = "CEP",
            value = "86430000",
            onValueChange = {},
            onClearValue = {})
    }
}