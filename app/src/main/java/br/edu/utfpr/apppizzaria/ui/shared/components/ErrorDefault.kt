package br.edu.utfpr.apppizzaria.ui.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.apppizzaria.R
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@Composable
fun ErrorDefault(
    modifier: Modifier = Modifier,
    text: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.CloudOff,
            contentDescription = stringResource(R.string.error_default_content_description_icon),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(80.dp)
        )
        Text(
            modifier = Modifier.padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp
            ),
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp
            ),
            text = stringResource(R.string.error_default_try_again_message),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        ElevatedButton(
            onClick = onRetry,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.error_default_try_again))
        }
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun ErrorDefaultPreview() {
    AppPizzariaTheme {
        ErrorDefault(
            onRetry = {},
            text = "Deu ruim!!"
        )
    }
}