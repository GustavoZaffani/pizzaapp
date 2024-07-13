package br.edu.utfpr.apppizzaria.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.apppizzaria.data.network.errors.ErrorData
import br.edu.utfpr.apppizzaria.data.network.errors.ErrorField
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDetails(
    modifier: Modifier = Modifier,
    errorData: ErrorData,
    showModal: Boolean,
    onDismissModal: () -> Unit
) {

    if (showModal) {
        ModalBottomSheet(
            modifier = modifier
                .fillMaxHeight(0.5f),
            onDismissRequest = onDismissModal,
            sheetState = rememberModalBottomSheetState()
        ) {
            ErrorContent(
                errorData = errorData
            )
        }
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
    errorData: ErrorData
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(8.dp)
        ) {
            Text(
                text = errorData.title,
                style = MaterialTheme.typography.titleLarge
                    .copy(
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorData.message.isNotBlank()) {
            Text(
                text = errorData.message,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontStyle = FontStyle.Italic
                )
            )
        }

        if (errorData.fieldErrors.isNotEmpty()) {
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            errorData.fieldErrors.forEach { fieldError ->
                Text(
                    text = fieldError.error,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontStyle = FontStyle.Italic
                    ),
                )
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun ErrorContentPreview() {
    AppPizzariaTheme {
        ErrorContent(
            errorData = ErrorData(
                title = "Ocorreu um erro",
                message = "Login inválido",
                fieldErrors = listOf(
                    ErrorField(field = "email", error = "Email é obrigatórioa"),
                    ErrorField(field = "password", error = "Senha é obrigatóri")
                )
            )
        )
    }
}