package br.edu.utfpr.apppizzaria.ui.shared.components.form

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.apppizzaria.ui.shared.visualtransformations.PhoneVisualTransformation
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@Composable
fun PhoneField(
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
        onValueChange = { text ->
            var newText = text.filter { it.isDigit() }
            if (newText.length > 11) newText = newText.dropLast(1)
            onValueChange(newText)
        },
        errorMessageCode = errorMessageCode,
        enabled = enabled,
        keyboardCapitalization = KeyboardCapitalization.None,
        keyboardImeAction = keyboardImeAction,
        keyboardType = KeyboardType.Phone,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Phone,
                contentDescription = "Telefone"
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
        visualTransformation = PhoneVisualTransformation()
    )
}

@Preview(showBackground = true)
@Composable
private fun PhoneFieldPreview() {
    AppPizzariaTheme {
        PhoneField(
            label = "Telefone",
            value = "46999558659",
            onValueChange = {},
            onClearValue = {})
    }
}